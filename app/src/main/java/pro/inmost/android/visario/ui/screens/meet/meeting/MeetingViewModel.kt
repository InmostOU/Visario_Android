package pro.inmost.android.visario.ui.screens.meet.meeting

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazonaws.services.chime.sdk.meetings.audiovideo.*
import com.amazonaws.services.chime.sdk.meetings.audiovideo.video.VideoTileObserver
import com.amazonaws.services.chime.sdk.meetings.audiovideo.video.VideoTileState
import com.amazonaws.services.chime.sdk.meetings.device.MediaDevice
import com.amazonaws.services.chime.sdk.meetings.device.MediaDeviceType
import com.amazonaws.services.chime.sdk.meetings.realtime.RealtimeObserver
import com.amazonaws.services.chime.sdk.meetings.session.DefaultMeetingSession
import com.amazonaws.services.chime.sdk.meetings.session.MeetingSession
import com.amazonaws.services.chime.sdk.meetings.session.MeetingSessionConfiguration
import com.amazonaws.services.chime.sdk.meetings.session.MeetingSessionStatus
import com.amazonaws.services.chime.sdk.meetings.utils.logger.ConsoleLogger
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.usecases.meetings.CreateMeetingUseCase
import pro.inmost.android.visario.domain.usecases.meetings.DeleteAttendeeUseCase
import pro.inmost.android.visario.domain.usecases.meetings.GetAttendeeUseCase
import pro.inmost.android.visario.domain.usecases.meetings.JoinMeetingUseCase
import pro.inmost.android.visario.domain.usecases.profile.FetchProfileUseCase
import pro.inmost.android.visario.ui.entities.meeting.AttendeeUI
import pro.inmost.android.visario.ui.entities.meeting.toAttendeeUI
import pro.inmost.android.visario.ui.entities.profile.toUIProfile
import pro.inmost.android.visario.ui.utils.SingleLiveEvent
import pro.inmost.android.visario.ui.utils.log
import kotlin.random.Random

class MeetingViewModel(
    private val createUseCase: CreateMeetingUseCase,
    private val joinMeetingUseCase: JoinMeetingUseCase,
    private val deleteAttendeeUseCase: DeleteAttendeeUseCase,
    private val getAttendeeUseCase: GetAttendeeUseCase,
    private val fetchProfileUseCase: FetchProfileUseCase
) : ViewModel() {
    private val sessionListener = SessionListener()
    var meetingId: String = ""
        private set
    private val attendees = mutableListOf<AttendeeUI>()
    private var _currentAttendee = MutableLiveData<AttendeeUI>()
    var currentAttendee: LiveData<AttendeeUI> = _currentAttendee

    private val _memberJoinEvent = SingleLiveEvent<AttendeeUI>()
    private val _memberLeftEvent = SingleLiveEvent<String>()

    val memberJoinEvent: LiveData<AttendeeUI> = _memberJoinEvent
    val memberLeftEvent: LiveData<String> = _memberLeftEvent

    private val _showProgressBar = MutableLiveData(false)
    val showProgressBar: LiveData<Boolean> = _showProgressBar
    private val _showToast = SingleLiveEvent<Int>()
    val showToast: LiveData<Int> = _showToast

    private val _sessionStarted = MutableLiveData(false)
    val sessionStarted: LiveData<Boolean> = _sessionStarted

    private var meetingSession: MeetingSession? = null
    private val audioVideo: AudioVideoFacade?
        get() = meetingSession?.audioVideo

    private val audioDevices: List<MediaDevice>
        get() = audioVideo?.listAudioDevices() ?: listOf()

    private val audioDeviceTypes: List<MediaDeviceType>
        get() =  audioDevices.map { it.type }

    private fun getAttendee(attendeeId: String): AttendeeUI? {
        return attendees.find { it.attendeeId == attendeeId }
    }

    private suspend fun createAttendee(attendeeInfo: AttendeeInfo): AttendeeUI? {
        return attendees.find { it.attendeeId == attendeeInfo.attendeeId }
            ?: getAttendeeUseCase.get(attendeeInfo.externalUserId, meetingId)
                .getOrNull()
                ?.toAttendeeUI(attendeeInfo.attendeeId)
            ?: AttendeeUI(
                userId = kotlin.runCatching { attendeeInfo.externalUserId.toLong() }.getOrDefault(
                    Random.nextLong()
                ),
                attendeeId = attendeeInfo.attendeeId,
                name = "Member-${Random.nextInt()}"
            )
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
                _showToast.value = R.string.creation_failed
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
            addAudioVideoObserver(sessionListener)
            addRealtimeObserver(sessionListener)
            addVideoTileObserver(sessionListener)
            start()
            startLocalVideo()
            startRemoteVideo()
        }
        _sessionStarted.value = true
    }

    private fun bindVideoView(tileState: VideoTileState) {
        viewModelScope.launch {
            if (tileState.isLocalTile && currentAttendee.value == null){
                addMyself(tileState.attendeeId)
            }
            val attendee = getAttendee(tileState.attendeeId)
            val videoView = attendee?.videoView
            if (videoView != null) {
                audioVideo?.bindVideoView(videoView, tileState.tileId)
                attendee.turnOnCam()
            }
        }
    }

    private suspend fun addMyself(attendeeId: String) {
        fetchProfileUseCase.fetch().firstOrNull()?.let {
            val profile = it.toUIProfile()
            val attendee = AttendeeUI(
                userId = profile.id,
                attendeeId = attendeeId,
                name = profile.fullName,
                image = profile.image,
                isMe = true
            )
            _currentAttendee.value = attendee
            addAttendee(attendee)
        }
    }

    private fun unbindVideoView(tileState: VideoTileState) {
        getAttendee(tileState.attendeeId)?.let {
            it.turnOffCam()
            audioVideo?.unbindVideoView(tileState.tileId)
        }
    }

    private fun addAttendee(attendee: AttendeeUI){
        if (!attendees.contains(attendee)){
            attendees.add(attendee)
            _memberJoinEvent.value = attendee
        }
    }

    private fun removeAttendee(attendeeId: String){
        attendees.removeIf { it.attendeeId == attendeeId }
        _memberLeftEvent.value = attendeeId
    }

    fun toggleVideo(attendee: AttendeeUI) {
        audioVideo?.apply {
            if (attendee.isCameraOn()) {
                stopLocalVideo()
                attendee.turnOffCam()
            } else {
                startLocalVideo()
                attendee.turnOnCam()
            }
        }
    }

    fun toggleMic(attendee: AttendeeUI) {
        audioVideo?.apply {
            if (attendee.isMicOn()) {
                realtimeLocalMute()
                attendee.turnOffMic()
            } else {
                realtimeLocalUnmute()
                attendee.turnOnMic()
            }
        }
    }

    suspend fun leaveMeeting() {
        audioVideo?.apply {
            removeAudioVideoObserver(sessionListener)
            removeRealtimeObserver(sessionListener)
            removeVideoTileObserver(sessionListener)
            stopLocalVideo()
            stopContentShare()
            stopRemoteVideo()
            stop()
        }
        deleteAttendeeUseCase.deleteCurrentUser(meetingId)
    }

    fun setAudioDevice(type: MediaDeviceType){
        audioDevices.forEach {
            if (it.type == type){
                setAudioDevice(it)
            }
        }
    }

    fun setAudioDevice(device: MediaDevice){
        audioVideo?.chooseAudioDevice(device)
    }

    fun switchCamera() {
        audioVideo?.switchCamera()
    }


    override fun onCleared() {
        viewModelScope.launch {
            leaveMeeting()
        }.invokeOnCompletion {
            super.onCleared()
        }
    }


    private inner class SessionListener : VideoTileObserver, RealtimeObserver, AudioVideoObserver {
        override fun onVideoTileAdded(tileState: VideoTileState) {
            log("Video tile added, titleId: ${tileState.tileId}, attendeeId: ${tileState.attendeeId}, isContent ${tileState.isContent}")
            bindVideoView(tileState)
        }

        override fun onVideoTilePaused(tileState: VideoTileState) {
            log("Video tile paused, titleId: ${tileState.tileId}, attendeeId: ${tileState.attendeeId}")
        }

        override fun onVideoTileRemoved(tileState: VideoTileState) {
            log("Video tile removed, titleId: ${tileState.tileId}, attendeeId: ${tileState.attendeeId}")
            unbindVideoView(tileState)
        }

        override fun onVideoTileResumed(tileState: VideoTileState) {
            log("Video tile resumed, titleId: ${tileState.tileId}, attendeeId: ${tileState.attendeeId}")
        }

        override fun onVideoTileSizeChanged(tileState: VideoTileState) {
            log("Video tile size changed, titleId: ${tileState.tileId}, attendeeId: ${tileState.attendeeId}")
        }

        override fun onAttendeesDropped(attendeeInfo: Array<AttendeeInfo>) {
            attendeeInfo.forEach {
                log("${it.attendeeId} dropped from the meeting")
                removeAttendee(it.attendeeId)
            }
        }

        override fun onAttendeesJoined(attendeeInfo: Array<AttendeeInfo>) {
            attendeeInfo.forEach { info ->
                log("$info joined")
                viewModelScope.launch {
                    createAttendee(info)?.let { addAttendee(it) }
                }
            }
        }

        override fun onAttendeesLeft(attendeeInfo: Array<AttendeeInfo>) {
            attendeeInfo.forEach {
                log("${it.attendeeId} left")
                removeAttendee(it.attendeeId)
            }
        }

        override fun onAttendeesMuted(attendeeInfo: Array<AttendeeInfo>) {
            attendeeInfo.forEach {
                log("${it.attendeeId} muted")
                getAttendee(it.attendeeId)?.turnOffMic()
            }
        }

        override fun onAttendeesUnmuted(attendeeInfo: Array<AttendeeInfo>) {
            attendeeInfo.forEach {
                log("${it.attendeeId} unmuted")
                getAttendee(it.attendeeId)?.turnOnMic()
            }
        }

        override fun onSignalStrengthChanged(signalUpdates: Array<SignalUpdate>) {
            signalUpdates.forEach { (attendeeInfo, signalStrength) ->
                log("${attendeeInfo.attendeeId}'s signal strength changed: $signalStrength")
            }
        }

        override fun onVolumeChanged(volumeUpdates: Array<VolumeUpdate>) {}

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
}