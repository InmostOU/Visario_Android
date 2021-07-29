package pro.inmost.android.visario.ui.screens.chats.messages

import android.text.format.DateFormat
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import pro.inmost.android.visario.R
import pro.inmost.android.visario.core.data.chimeapi.messages.Message
import pro.inmost.android.visario.core.data.chimeapi.messages.MessageRequest
import pro.inmost.android.visario.core.domain.utils.log
import pro.inmost.android.visario.ui.boundaries.MessagesRepository
import pro.inmost.android.visario.ui.utils.toast
import java.text.SimpleDateFormat
import java.util.*

class MessagesViewModel(private val repository: MessagesRepository) : ViewModel() {
    private var channelArn: String = ""
    val message = MutableLiveData<String>()
    val myNewMessages = MutableLiveData<Message>()

    fun getMessages(arn: String) = liveData {
        channelArn = arn
        withContext(Dispatchers.IO) {
            repository.getMessages(arn)
        }.onSuccess { list ->
            val messages = list
                .sortedBy { it.createdTimestamp }
                .reversed()
            emit(messages)
        }.onFailure {
            log(it.message)
        }
    }

    fun sendMessage(view: View) {
        if (message.value.isNullOrBlank()) return

        // for test
        myNewMessages.value = createMessage()
        message.value = ""

        viewModelScope.launch {
            val request = MessageRequest(message.value!!, channelArn)
            withContext(Dispatchers.IO) {
                repository.sendMessage(request)
            }.onSuccess {
                message.value = ""
            }.onFailure {
                val message = it.message ?: view.context.getString(R.string.send_failed)
                view.context.toast(message)
            }
        }
    }

    private fun createMessage(): Message{
        return  Message(
            messageId = UUID.randomUUID().toString(),
            content = message.value ?: "",
            createdTimestamp = DateFormat.format("yyyy-MM-dd hh:mm:ss", System.currentTimeMillis()).toString(),
            lastEditedTimestamp = DateFormat.format("yyyy-MM-dd hh:mm:ss", System.currentTimeMillis()).toString(),
            metadata = "",
            redacted = false,
            senderArn = "",
            senderName = "Mr Myself",
            type = "",
            fromCurrentUser = true
        )
    }
}