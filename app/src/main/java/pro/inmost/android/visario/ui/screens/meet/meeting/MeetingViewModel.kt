package pro.inmost.android.visario.ui.screens.meet.meeting

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazonaws.services.chime.sdk.meetings.audiovideo.*
import com.amazonaws.services.chime.sdk.meetings.audiovideo.video.VideoTileObserver
import com.amazonaws.services.chime.sdk.meetings.audiovideo.video.VideoTileState
import com.amazonaws.services.chime.sdk.meetings.realtime.RealtimeObserver
import com.amazonaws.services.chime.sdk.meetings.session.DefaultMeetingSession
import com.amazonaws.services.chime.sdk.meetings.session.MeetingSession
import com.amazonaws.services.chime.sdk.meetings.session.MeetingSessionConfiguration
import com.amazonaws.services.chime.sdk.meetings.session.MeetingSessionStatus
import com.amazonaws.services.chime.sdk.meetings.utils.logger.ConsoleLogger
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.usecases.meetings.CreateMeetingUseCase
import pro.inmost.android.visario.domain.usecases.meetings.DeleteAttendeeUseCase
import pro.inmost.android.visario.domain.usecases.meetings.GetAttendeeUseCase
import pro.inmost.android.visario.domain.usecases.meetings.JoinMeetingUseCase
import pro.inmost.android.visario.ui.entities.meeting.AttendeeUI
import pro.inmost.android.visario.ui.utils.SingleLiveEvent
import pro.inmost.android.visario.ui.utils.log

class MeetingViewModel(
    private val createUseCase: CreateMeetingUseCase,
    private val joinMeetingUseCase: JoinMeetingUseCase,
    private val deleteAttendeeUseCase: DeleteAttendeeUseCase,
    private val getAttendeeUseCase: GetAttendeeUseCase
) : ViewModel() {
    private val _memberJoinEvent = SingleLiveEvent<AttendeeUI>()
    private val _memberLeaveEvent = SingleLiveEvent<String>()

    val memberJoinEvent: LiveData<AttendeeUI> = _memberJoinEvent
    val memberLeaveEvent: LiveData<String> = _memberLeaveEvent

    private val _micOn = MutableLiveData(true)
    private val _camOn = MutableLiveData(true)
    val micOn: LiveData<Boolean> = _micOn
    val camOn: LiveData<Boolean> = _camOn

    private val _showProgressBar = MutableLiveData(false)
    private val _showToast = SingleLiveEvent<Int>()
    val showProgressBar : LiveData<Boolean> = _showProgressBar
    val showToast : LiveData<Int> = _showToast

    var meetingId: String = ""
        private set
    private val _sessionStarted = MutableLiveData(false)
    val sessionStarted: LiveData<Boolean> = _sessionStarted

    private var meetingSession: MeetingSession? = null
    private val audioVideo: AudioVideoFacade?
        get() = meetingSession?.audioVideo

    private val videoTileObserver = object : VideoTileObserver {

        override fun onVideoTileAdded(tileState: VideoTileState) {
            log("Video tile added, titleId: ${tileState.tileId}, attendeeId: ${tileState.attendeeId}, isContent ${tileState.isContent}")
            viewModelScope.launch {
                getAttendee(tileState)?.let {
                    bindVideoView(it)
                }
            }
        }

        override fun onVideoTilePaused(tileState: VideoTileState) {
            log("Video tile paused, titleId: ${tileState.tileId}, attendeeId: ${tileState.attendeeId}")
        }

        override fun onVideoTileRemoved(tileState: VideoTileState) {
            log("Video tile removed, titleId: ${tileState.tileId}, attendeeId: ${tileState.attendeeId}")
            removeMember(tileState.attendeeId)
        }

        override fun onVideoTileResumed(tileState: VideoTileState) {
            log("Video tile resumed, titleId: ${tileState.tileId}, attendeeId: ${tileState.attendeeId}")
        }

        override fun onVideoTileSizeChanged(tileState: VideoTileState) {
            log("Video tile size changed, titleId: ${tileState.tileId}, attendeeId: ${tileState.attendeeId}")
        }
    }

    private val realtimeObserver = object : RealtimeObserver {
        override fun onAttendeesDropped(attendeeInfo: Array<AttendeeInfo>) {
            attendeeInfo.forEach { log("${it.attendeeId} dropped from the meeting") }
        }

        override fun onAttendeesJoined(attendeeInfo: Array<AttendeeInfo>) {
            attendeeInfo.forEach { log("${it.attendeeId} joined") }
        }

        override fun onAttendeesLeft(attendeeInfo: Array<AttendeeInfo>) {
            attendeeInfo.forEach { log("${it.attendeeId} left") }
        }

        override fun onAttendeesMuted(attendeeInfo: Array<AttendeeInfo>) {
            attendeeInfo.forEach { log("${it.attendeeId} muted") }
        }

        override fun onAttendeesUnmuted(attendeeInfo: Array<AttendeeInfo>) {
            attendeeInfo.forEach { log("${it.attendeeId} unmuted") }
        }

        override fun onSignalStrengthChanged(signalUpdates: Array<SignalUpdate>) {
            signalUpdates.forEach { (attendeeInfo, signalStrength) ->
                log("${attendeeInfo.attendeeId}'s signal strength changed: $signalStrength")
            }
        }

        override fun onVolumeChanged(volumeUpdates: Array<VolumeUpdate>) {
            volumeUpdates.forEach { (attendeeInfo, volumeLevel) ->
                log("${attendeeInfo.attendeeId}'s volume changed: $volumeLevel")
            }
        }
    }

    private val audioVideoObserver = object : AudioVideoObserver {
        override fun onAudioSessionCancelledReconnect() {
            log("onAudioSessionCancelledReconnect")
        }

        override fun onAudioSessionDropped() {
            log("onAudioSessionDropped")
        }

        override fun onAudioSessionStarted(reconnecting: Boolean) {
            log("onAudioSessionStarted")
        }

        override fun onAudioSessionStartedConnecting(reconnecting: Boolean) {
            log("onAudioSessionStartedConnecting")
        }

        override fun onAudioSessionStopped(sessionStatus: MeetingSessionStatus) {
            log("onAudioSessionStopped")
        }

        override fun onConnectionBecamePoor() {
            log("onConnectionBecamePoor")
        }

        override fun onConnectionRecovered() {
            log("onConnectionRecovered")
        }

        override fun onVideoSessionStarted(sessionStatus: MeetingSessionStatus) {
            log("onVideoSessionStarted: ${sessionStatus.statusCode}")
        }

        override fun onVideoSessionStartedConnecting() {
            log("onVideoSessionStartedConnecting")
        }

        override fun onVideoSessionStopped(sessionStatus: MeetingSessionStatus) {
            log("onVideoSessionStopped: ${sessionStatus.statusCode}")
        }

    }

    private suspend fun getAttendee(tileState: VideoTileState): AttendeeUI? {
        log("get attendee: ${tileState.attendeeId}")
        getAttendeeUseCase.get(tileState.attendeeId).onSuccess { attendee ->
            log("get attendee success")
            return AttendeeUI(
                tileState = tileState,
                audioVideoFacade = audioVideo!!,
                userId = attendee.userId,
                image = attendee.image,
                name = attendee.name,
                isMe = tileState.isLocalTile
            )
        }
        return null
    }

    fun createMeeting(context: Context) {
        _showProgressBar.value = true
        viewModelScope.launch {
            createUseCase.create().onSuccess {
                _showProgressBar.value = false
                meetingId = it.meetingId
                startSession(it, context)
            }.onFailure {
                _showProgressBar.value = false
                _showToast.value = R.string.creation_fails
            }
        }
    }

    fun joinMeeting(context: Context, meetingId: String) {
        _showProgressBar.value = true
        this.meetingId = meetingId
        viewModelScope.launch {
            joinMeetingUseCase.join(meetingId).onSuccess {
                _showProgressBar.value = false
                startSession(it, context)
            }.onFailure {
                _showProgressBar.value = false
                _showToast.value = R.string.join_failed
            }
        }
    }

    private fun startSession(
        config: MeetingSessionConfiguration,
        context: Context
    ) {
        meetingSession = DefaultMeetingSession(config, ConsoleLogger(), context)
        audioVideo?.apply {
            addRealtimeObserver(realtimeObserver)
            addAudioVideoObserver(audioVideoObserver)
            addVideoTileObserver(videoTileObserver)
            start()
            startLocalVideo()
            startRemoteVideo()
        }
        _sessionStarted.value = true
    }

    private fun bindVideoView(attendee: AttendeeUI) {
        _memberJoinEvent.value = attendee
    }

    fun toggleVideo(){
        _camOn.value = !_camOn.value!!
        audioVideo?.apply {
            if (camOn.value!!){
                startRemoteVideo()
                startLocalVideo()
            } else {
                stopRemoteVideo()
                stopLocalVideo()
            }
        }
    }

    fun toggleMic(){
        _micOn.value = !_micOn.value!!
        audioVideo?.apply {
            if (micOn.value!!){
                realtimeLocalUnmute()
            } else {
                realtimeLocalMute()
            }
        }
    }

    fun removeMember(attendeeId: String){
        _memberLeaveEvent.value = attendeeId
    }

    suspend fun leaveMeeting() {
        audioVideo?.apply {
            stopLocalVideo()
            stopContentShare()
            stopRemoteVideo()
            stop()
        }
        deleteAttendeeUseCase.deleteMyself(meetingId)
    }

    fun switchCamera() {
        audioVideo?.switchCamera()
    }
}