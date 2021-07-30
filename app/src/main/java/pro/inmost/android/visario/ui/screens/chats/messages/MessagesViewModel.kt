package pro.inmost.android.visario.ui.screens.chats.messages

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import pro.inmost.android.visario.R
import pro.inmost.android.visario.data.network.chimeapi.messages.Message
import pro.inmost.android.visario.data.network.chimeapi.messages.MessageRequest
import pro.inmost.android.visario.domain.boundaries.MessagesNetworkRepository
import pro.inmost.android.visario.domain.usecases.MessagingUseCase
import pro.inmost.android.visario.domain.utils.log

class MessagesViewModel(private val messagingUseCase: MessagingUseCase) : ViewModel() {
    private var channelArn: String = ""
    val message = MutableLiveData<String>()
    private var data: List<Message> = listOf()

    fun observeMessages(arn: String) = liveData {
        channelArn = arn
        messagingUseCase.observeMessages(arn).collect { result ->
            result.onSuccess { list ->
                data = list.sortedByDescending { it.createdTimestamp }
                emit(data)
            }.onFailure {
                log(it.message)
            }
        }
    }

    fun sendMessage(view: View) {
        if (message.value.isNullOrBlank()) return

        val request = MessageRequest(message.value!!, channelArn)
        message.value = ""

        viewModelScope.launch {
            messagingUseCase.sendMessage(request).onSuccess {
                message.value = ""
            }.onFailure {
                val message = it.message ?: view.context.getString(R.string.send_failed)
                log(message)
            }
        }
    }

    fun saveMessages(){
        viewModelScope.launch {
            messagingUseCase.saveMessage(data)
        }
    }
}