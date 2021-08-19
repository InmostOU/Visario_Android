package pro.inmost.android.visario.ui.screens.channels.messages

import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pro.inmost.android.visario.domain.entities.message.Message
import pro.inmost.android.visario.domain.usecases.channels.LeaveChannelUseCase
import pro.inmost.android.visario.domain.usecases.messages.FetchMessagesUseCase
import pro.inmost.android.visario.domain.usecases.messages.SendMessageUseCase
import pro.inmost.android.visario.domain.usecases.profile.FetchProfileUseCase
import pro.inmost.android.visario.ui.entities.message.MessageUI
import pro.inmost.android.visario.ui.entities.message.MessageUIStatus
import pro.inmost.android.visario.ui.entities.message.toDomainMessage
import pro.inmost.android.visario.ui.entities.message.toUIMessages
import pro.inmost.android.visario.ui.entities.profile.ProfileUI
import pro.inmost.android.visario.ui.entities.profile.toUIProfile
import pro.inmost.android.visario.ui.utils.SingleLiveEvent
import pro.inmost.android.visario.ui.utils.log
import java.util.*

class MessagesViewModel(
    private val fetchMessagesUseCase: FetchMessagesUseCase,
    private val fetchProfileUseCase: FetchProfileUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val leaveChannelUseCase: LeaveChannelUseCase,
) : ViewModel() {
    private var profile: ProfileUI? = null
    var currentChannelUrl: String = ""
        private set
    val message = MutableLiveData<String>()
    private val _closeChannel = SingleLiveEvent<Unit>()
    val closeChannel: LiveData<Unit> = _closeChannel

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            fetchProfileUseCase.fetch().map { it.toUIProfile() }.collect {
                profile = it
            }
        }
    }

    fun observeMessages(channelUrl: String): LiveData<List<MessageUI>> {
        currentChannelUrl = channelUrl
        return fetchMessagesUseCase.fetch(channelUrl).map { it.toUIMessages() }.asLiveData()
    }

    fun sendMessage() {
        log("send click")
        if (message.value.isNullOrBlank()) return
        log("message not blank")
        val messageForSending = createMessage() ?: return
        log("message created")
        message.value = ""
        viewModelScope.launch {
            sendMessageUseCase.send(messageForSending)
        }
    }

    private fun createMessage(): Message? {
        if (profile == null || message.value == null) return null
        return MessageUI(
            messageId = UUID.randomUUID().toString(),
            text = message.value!!,
            channelUrl = currentChannelUrl,
            senderUrl = profile?.userUrl ?: "",
            senderName = profile?.fullName ?: "",
            createdTimestamp = System.currentTimeMillis(),
            fromCurrentUser = true,
            readByMe = true,
            status = MessageUIStatus.SENDING
        ).toDomainMessage()
    }

    fun leaveChannel() {
        viewModelScope.launch {
            leaveChannelUseCase.leave(currentChannelUrl).onSuccess {
                _closeChannel.call()
            }
        }
    }
}