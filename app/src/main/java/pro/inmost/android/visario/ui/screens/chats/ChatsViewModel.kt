package pro.inmost.android.visario.ui.screens.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import pro.inmost.android.visario.core.domain.entities.Chat
import pro.inmost.android.visario.ui.boundaries.ChatsRepository

class ChatsViewModel(private val repository: ChatsRepository<Chat>) : ViewModel() {
    private val chatsRequestTimeout = 5000L

    fun observeChats() = repository.observeChats(chatsRequestTimeout).asLiveData()
}