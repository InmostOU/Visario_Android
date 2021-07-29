package pro.inmost.android.visario.ui.screens.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.core.data.chimeapi.channels.model.Channel
import pro.inmost.android.visario.core.domain.utils.log
import pro.inmost.android.visario.ui.boundaries.ChatsRepository
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class ChatsViewModel(private val repository: ChatsRepository<Channel>) : ViewModel() {
    private val chatsRequestTimeout = 5000L
    private val _onChatClick = SingleLiveEvent<Channel>()
    val onChatClick: LiveData<Channel> = _onChatClick

    fun observeChats() = liveData {
        while (viewModelScope.isActive){
            withContext(IO){
                repository.getChats()
            }.onSuccess {
                log("success: $it")
                emit(it)
            }.onFailure {
                log("failure: $it")
                log(it.message)
            }
            delay(chatsRequestTimeout)
        }
    }

    fun onItemClick(item: Channel) {
        _onChatClick.postValue(item)
    }
}