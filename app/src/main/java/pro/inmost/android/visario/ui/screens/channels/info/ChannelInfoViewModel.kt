package pro.inmost.android.visario.ui.screens.channels.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCase
import pro.inmost.android.visario.ui.entities.channel.ChannelUI
import pro.inmost.android.visario.ui.entities.channel.toUIChannel
import pro.inmost.android.visario.utils.SingleLiveEvent

class ChannelInfoViewModel(
    private val fetchChannelsUseCase: FetchChannelsUseCase
) : ViewModel() {
    private val _openMemberListEvent = SingleLiveEvent<ChannelUI>()
    val openMemberListEvent: LiveData<ChannelUI> = _openMemberListEvent

    private var currentChannel: ChannelUI? = null

    fun loadChannel(channelArn: String) = liveData {
        fetchChannelsUseCase.getChannel(channelArn)
            .map { it.toUIChannel() }
            .onSuccess { channel ->
                currentChannel = channel
                emit(channel)
            }
    }

    fun onMembersClick() {
        currentChannel?.let {
            _openMemberListEvent.value = it
        }
    }
}