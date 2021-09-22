package pro.inmost.android.visario.ui.dialogs.inviter.meeting

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCase
import pro.inmost.android.visario.domain.usecases.meetings.SendInvitationUseCase
import pro.inmost.android.visario.ui.base.BaseViewModel
import pro.inmost.android.visario.ui.entities.channel.ChannelUI
import pro.inmost.android.visario.ui.entities.channel.toUIChannels

class MeetingChannelsInviterViewModel(
    private val fetchChannelsUseCase: FetchChannelsUseCase,
    private val sendInvitationUseCase: SendInvitationUseCase
) : BaseViewModel() {
    private var meetingId: String = ""

    fun sendInvitation(channel: ChannelUI) {
        channel.disabled.set(true)
        viewModelScope.launch {
            sendInvitationUseCase.send(meetingId, channel.url).onSuccess {
                channel.invited.set(true)
            }.onFailure {
                channel.disabled.set(false)
                sendNotification(R.string.invitation_failed)
            }
        }
    }

    fun setMeetingId(id: String) {
        meetingId = id
    }

    fun loadChannels() = fetchChannelsUseCase.getChannels().map { it.toUIChannels() }.asLiveData()
}