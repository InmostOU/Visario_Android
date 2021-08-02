package pro.inmost.android.visario.ui.screens.chats.messages

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.entities.Channel
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCase
import pro.inmost.android.visario.domain.usecases.channels.SaveChannelsUseCase
import pro.inmost.android.visario.domain.usecases.messages.SendMessageUseCase
import pro.inmost.android.visario.ui.utils.log

class MessagesViewModel(
    private val fetchChannelsUseCase: FetchChannelsUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val saveChannelsUseCase: SaveChannelsUseCase
) : ViewModel() {
    private var currentChannelUrl: String = ""
    val message = MutableLiveData<String>()
    private var data: Channel? = null

    fun observeChannel(channelUrl: String) = liveData {
        currentChannelUrl = channelUrl
        fetchChannelsUseCase.observeChannel(channelUrl).collect { channel ->
            data = channel
            emit(data!!)
        }
    }

    fun sendMessage(view: View) {
        if (message.value.isNullOrBlank()) return
        message.value = ""

        viewModelScope.launch {
            sendMessageUseCase.send(message.value!!, currentChannelUrl).onSuccess {
                message.value = ""
            }.onFailure {
                val message = it.message ?: view.context.getString(R.string.send_failed)
                log(message)
            }
        }
    }

    fun saveChannel() {
        viewModelScope.launch {
            data?.let {
                saveChannelsUseCase.save(it)
            }
        }
    }
}