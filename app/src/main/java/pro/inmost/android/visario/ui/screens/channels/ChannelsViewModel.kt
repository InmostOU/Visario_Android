package pro.inmost.android.visario.ui.screens.channels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCase
import pro.inmost.android.visario.ui.entities.channel.ChannelUI
import pro.inmost.android.visario.ui.entities.channel.toUIChannels
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class ChannelsViewModel(
    private val fetchUseCase: FetchChannelsUseCase
) : ViewModel() {
    private val _onChatClick = SingleLiveEvent<ChannelUI>()
    val onChatClick: LiveData<ChannelUI> = _onChatClick

    val channels: LiveData<List<ChannelUI>>
        get() =  fetchUseCase.getChannels().asLiveData().map { it.toUIChannels() }

    fun onItemClick(item: ChannelUI) {
        _onChatClick.postValue(item)
    }
}