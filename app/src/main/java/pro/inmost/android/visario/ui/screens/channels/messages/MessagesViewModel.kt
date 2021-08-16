package pro.inmost.android.visario.ui.screens.channels.messages

import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.usecases.messages.FetchMessagesUseCase
import pro.inmost.android.visario.domain.usecases.messages.SendMessageUseCase
import pro.inmost.android.visario.ui.entities.MessageUI
import pro.inmost.android.visario.ui.entities.toUIMessages
import pro.inmost.android.visario.ui.utils.log

class MessagesViewModel(
    private val fetchMessagesUseCase: FetchMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {
    private var currentChannelUrl: String = ""
    val message = MutableLiveData<String>()

    fun observeMessages(channelUrl: String): LiveData<List<MessageUI>> {
        currentChannelUrl = channelUrl
        return fetchMessagesUseCase.fetch(channelUrl).asLiveData().map { it.toUIMessages() }
    }

    fun sendMessage(view: View) {
        if (message.value.isNullOrBlank()) return
        val messageText = message.value!!
        message.value = ""
        viewModelScope.launch {
            sendMessageUseCase.send(
                channel = currentChannelUrl,
                message = messageText
            ).onFailure {
             //   message.value = messageText
                val message = it.message ?: view.context.getString(R.string.send_failed)
                log(message)
            }
        }
    }
}