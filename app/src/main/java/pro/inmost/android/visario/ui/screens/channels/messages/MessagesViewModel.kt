package pro.inmost.android.visario.ui.screens.channels.messages

import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pro.inmost.android.visario.domain.usecases.channels.LeaveChannelUseCase
import pro.inmost.android.visario.domain.usecases.messages.FetchMessagesUseCase
import pro.inmost.android.visario.domain.usecases.messages.SendMessageUseCase
import pro.inmost.android.visario.domain.usecases.messages.UpdateMessagesReadStatusUseCase
import pro.inmost.android.visario.domain.usecases.profile.FetchProfileUseCase
import pro.inmost.android.visario.ui.entities.message.MessageUI
import pro.inmost.android.visario.ui.entities.message.toUIMessages
import pro.inmost.android.visario.ui.entities.profile.ProfileUI
import pro.inmost.android.visario.ui.entities.profile.toUIProfile
import pro.inmost.android.visario.ui.utils.SingleLiveEvent
import pro.inmost.android.visario.ui.utils.extensions.meetingId

class MessagesViewModel(
    private val fetchMessagesUseCase: FetchMessagesUseCase,
    private val fetchProfileUseCase: FetchProfileUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val leaveChannelUseCase: LeaveChannelUseCase,
    private val updateReadStatusUseCase: UpdateMessagesReadStatusUseCase
) : ViewModel() {
    private var profile: ProfileUI? = null
    var currentChannelUrl: String = ""
        private set
    val message = MutableLiveData<String>()
    private val _closeChannel = SingleLiveEvent<Unit>()
    val closeChannel: LiveData<Unit> = _closeChannel

    private val _joinMeetingEvent = SingleLiveEvent<String>()
    val joinMeetingEvent: LiveData<String> = _joinMeetingEvent

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
        if (message.value.isNullOrBlank()) return
        val messageForSending =  message.value ?: return
        message.value = ""
        viewModelScope.launch {
            sendMessageUseCase.send(messageForSending, currentChannelUrl)
        }
    }

    fun leaveChannel() {
        viewModelScope.launch {
            leaveChannelUseCase.leave(currentChannelUrl).onSuccess {
                _closeChannel.call()
            }
        }
    }

    fun updateReadStatus(){
        viewModelScope.launch {
            updateReadStatusUseCase.updateAll(currentChannelUrl, true)
        }
    }

    fun onMessageClick(message: MessageUI){

    }

    fun onInvitationClick(message: MessageUI){
        if (message.isMeetingInvitation){
            message.text.meetingId?.let {
                _joinMeetingEvent.value = it
            }
        }
    }
}