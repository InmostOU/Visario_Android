package pro.inmost.android.visario.ui.screens.chats.messages

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import pro.inmost.android.visario.R
import pro.inmost.android.visario.core.data.chimeapi.messages.MessageRequest
import pro.inmost.android.visario.core.domain.utils.log
import pro.inmost.android.visario.ui.boundaries.MessagesRepository
import pro.inmost.android.visario.ui.utils.toast

class MessagesViewModel(private val repository: MessagesRepository) : ViewModel() {
    private var channelArn: String = ""
    val message = MutableLiveData<String>()

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

        viewModelScope.launch {
            val request = MessageRequest(message.value!!, channelArn)
            withContext(Dispatchers.IO) {
                repository.sendMessage(request)
            }.onSuccess {
                message.value = ""
            }.onFailure {
                view.context.toast(R.string.send_failed)
            }
        }

    }
}