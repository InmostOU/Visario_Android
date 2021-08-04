package pro.inmost.android.visario.ui.screens.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pro.inmost.android.visario.domain.entities.Channel
import pro.inmost.android.visario.domain.usecases.channels.ObserveChannelsUseCase
import pro.inmost.android.visario.domain.usecases.channels.SaveChannelsUseCase
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class ChatsViewModel(
    private val channelsUseCase: ObserveChannelsUseCase,
    private val saveChannelsUseCase: SaveChannelsUseCase
) : ViewModel() {
    private val _onChatClick = SingleLiveEvent<Channel>()
    val onChatClick: LiveData<Channel> = _onChatClick
    private var data = listOf<Channel>()

    fun observeChats() = liveData {
        channelsUseCase.observeChannels().collect { channels ->
            data = channels
            emit(data)
        }
    }

    fun onItemClick(item: Channel) {
        _onChatClick.postValue(item)
    }

    fun saveChannels() {
        viewModelScope.launch {
            saveChannelsUseCase.save(*data.toTypedArray())
        }
    }
}