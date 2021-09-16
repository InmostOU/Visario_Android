package pro.inmost.android.visario.ui.dialogs.inviter.meeting.channels

import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCase
import pro.inmost.android.visario.domain.usecases.meetings.SendInvitationUseCase
import pro.inmost.android.visario.ui.entities.channel.ChannelUI
import pro.inmost.android.visario.ui.entities.channel.toUIChannels
import pro.inmost.android.visario.ui.utils.extensions.toast

class MeetingChannelsInviterViewModel(
    private val fetchChannelsUseCase: FetchChannelsUseCase,
    private val sendInvitationUseCase: SendInvitationUseCase
) : ViewModel() {
    private var meetingId: String = ""

    fun inviteGroup(view: View, channel: ChannelUI) {
        view.isEnabled = false
        viewModelScope.launch {
            sendInvitationUseCase.send(meetingId, channel.url).onSuccess {
                (view as Button).text = view.context.getString(R.string.sent)
            }.onFailure {
                view.isEnabled = true
                view.toast(R.string.invitation_failed)
            }
        }
    }

    fun setMeetingId(id: String) {
        meetingId = id
    }

    fun loadChannels() = fetchChannelsUseCase.getChannels().map { it.toUIChannels() }.asLiveData()
}