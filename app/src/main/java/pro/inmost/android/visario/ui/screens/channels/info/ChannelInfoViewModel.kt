package pro.inmost.android.visario.ui.screens.channels.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.map
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCase
import pro.inmost.android.visario.ui.entities.channel.ChannelUI
import pro.inmost.android.visario.ui.entities.channel.toUIChannel
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class ChannelInfoViewModel(
    private val fetchChannelsUseCase: FetchChannelsUseCase
) : ViewModel() {
    private val _openMemberListEvent = SingleLiveEvent<ChannelUI>()
    val openMemberListEvent: LiveData<ChannelUI> = _openMemberListEvent

    private var currentChannel: ChannelUI? = null

    fun loadChannel(channelArn: String): LiveData<ChannelUI> {
        return fetchChannelsUseCase.getChannel(channelArn)
            .map { channel ->
                channel.toUIChannel().also { currentChannel = it }
            }
            .asLiveData()
    }

    fun onMembersClick(){
        currentChannel?.let {
            _openMemberListEvent.value = it
        }
    }
}