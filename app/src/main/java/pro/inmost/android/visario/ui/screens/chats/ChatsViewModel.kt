package pro.inmost.android.visario.ui.screens.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.network.chimeapi.channels.model.Channel
import pro.inmost.android.visario.domain.utils.log
import pro.inmost.android.visario.domain.boundaries.ChannelsNetworkRepository
import pro.inmost.android.visario.domain.usecases.ChannelsUseCase
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class ChatsViewModel(private val channelsUseCase: ChannelsUseCase) : ViewModel() {
    private val _onChatClick = SingleLiveEvent<Channel>()
    val onChatClick: LiveData<Channel> = _onChatClick
    private var data = listOf<Channel>()

    fun observeChats() = liveData {
        channelsUseCase.observeChannels().collect { result ->
            result.onSuccess {
                data = it
                emit(it)
            }.onFailure {
                log("channels receiving failure: ${it.message}")
            }
        }
    }

    fun onItemClick(item: Channel) {
        _onChatClick.postValue(item)
    }

    fun saveChannels(){
        viewModelScope.launch {
            channelsUseCase.saveChannels(data)
        }
    }
}