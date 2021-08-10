package pro.inmost.android.visario.ui.screens.chats

import androidx.lifecycle.*
import pro.inmost.android.visario.domain.entities.Channel
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCase
import pro.inmost.android.visario.domain.usecases.channels.UpdateChannelUseCase
import pro.inmost.android.visario.ui.entities.ChannelUI
import pro.inmost.android.visario.ui.entities.toUIChannels
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class ChatsViewModel(
    private val fetchUseCase: FetchChannelsUseCase,
    private val updateChannelUseCase: UpdateChannelUseCase
) : ViewModel() {
    private val _onChatClick = SingleLiveEvent<ChannelUI>()
    val onChatClick: LiveData<ChannelUI> = _onChatClick

    val channels: LiveData<List<ChannelUI>>
        get() =  fetchUseCase.getChannels().asLiveData().map { it.toUIChannels() }

    fun onItemClick(item: ChannelUI) {
        _onChatClick.postValue(item)
    }
}