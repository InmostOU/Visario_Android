package pro.inmost.android.visario.ui.screens.channels.messages

import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.usecases.channels.AddMemberToChannelUseCase
import pro.inmost.android.visario.domain.usecases.channels.LeaveChannelUseCase
import pro.inmost.android.visario.domain.usecases.meetings.CreateMeetingUseCase
import pro.inmost.android.visario.domain.usecases.meetings.SendInvitationUseCase
import pro.inmost.android.visario.domain.usecases.messages.*
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
    private val editMessageUseCase: EditMessageUseCase,
    private val deleteMessageUseCase: DeleteMessageUseCase,
    private val leaveChannelUseCase: LeaveChannelUseCase,
    private val updateReadStatusUseCase: UpdateMessagesReadStatusUseCase,
    private val addMemberToChannelUseCase: AddMemberToChannelUseCase,
    private val createMeetingUseCase: CreateMeetingUseCase,
    private val sendInvitationUseCase: SendInvitationUseCase
) : ViewModel() {
    private var profile: ProfileUI? = null
    var currentChannelArn: String = ""
        private set
    val message = MutableLiveData<String>()
    private var messageForEdit: MessageUI? = null
    private val _closeChannel = SingleLiveEvent<Unit>()
    val closeChannel: LiveData<Unit> = _closeChannel

    private val _joinMeetingEvent = SingleLiveEvent<String>()
    val joinMeetingEvent: LiveData<String> = _joinMeetingEvent

    private val _showProgressBar = SingleLiveEvent(false)
    val showProgressBar: LiveData<Boolean> = _showProgressBar

    private val _editMode = SingleLiveEvent(false)
    val editMode: LiveData<Boolean> = _editMode

    private val _showKeyboard = SingleLiveEvent<Unit>()
    val showKeyboard: LiveData<Unit> = _showKeyboard

    private val _toggleEmojisView = SingleLiveEvent<Unit>()
    val toggleEmojisView: LiveData<Unit> = _toggleEmojisView

    private val _showToast = SingleLiveEvent<Int>()
    val showToast: LiveData<Int> = _showToast

    private val _isJoined = MutableLiveData<Boolean>()
    val isJoined: LiveData<Boolean> = _isJoined

    private val _emojisOpened = MutableLiveData(false)
    val emojisOpened: LiveData<Boolean> = _emojisOpened

    private val _openPopupMenu = SingleLiveEvent<Pair<View, MessageUI>>()
    val openPopupMenu: LiveData<Pair<View, MessageUI>> = _openPopupMenu

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

    fun joinChannel(view: View){
        view.isEnabled = false
        viewModelScope.launch {
            profile?.let {
                addMemberToChannelUseCase.invite(currentChannelArn, it.userUrl).onSuccess {
                    view.isEnabled = true
                    _isJoined.value = true
                }.onFailure {
                    view.isEnabled = true
                }
            }
        }
    }

    fun setJoined(joined: Boolean){
        _isJoined.value = joined
    }

    fun observeMessages(channelUrl: String): LiveData<List<MessageUI>> {
        currentChannelArn = channelUrl
        return fetchMessagesUseCase.fetch(channelUrl).map { it.toUIMessages() }.asLiveData()
    }

    fun sendMessage() {
        if (message.value.isNullOrBlank()) return
        val messageForSending =  message.value ?: return
        clearMessage()
        viewModelScope.launch {
            sendMessageUseCase.send(messageForSending, currentChannelArn)
        }
    }

    fun resendMessage(message: MessageUI) {
        viewModelScope.launch {
            sendMessageUseCase.resend(message.messageId)
        }
    }

    fun leaveChannel() {
        viewModelScope.launch {
            leaveChannelUseCase.leave(currentChannelArn).onSuccess {
                _closeChannel.call()
            }
        }
    }

    fun updateReadStatus(){
        viewModelScope.launch {
            updateReadStatusUseCase.updateAll(currentChannelArn, true)
        }
    }

    fun onMessageClick(view: View, message: MessageUI){
        _openPopupMenu.value = view to message
    }

    fun onInvitationClick(message: MessageUI){
        if (message.isMeetingInvitation){
            message.text.meetingId?.let {
                _joinMeetingEvent.value = it
            }
        }
    }

    fun editMessage(message: MessageUI) {
        _editMode.value = true
        messageForEdit = message
        this.message.value = message.text
        _showKeyboard.call()
    }

    fun deleteLocalMessage(message: MessageUI) {
        viewModelScope.launch {
            deleteMessageUseCase.deleteLocal(message.messageId)
        }
    }

    fun deleteMessage(message: MessageUI) {
        viewModelScope.launch {
            deleteMessageUseCase.delete(message.messageId)
        }
    }

    fun editDone(view: View){
        view.isEnabled = false
        view.alpha = 0.5f
        viewModelScope.launch {
            if (messageForEdit != null && message.value != null){
                editMessageUseCase.edit(
                    messageId = messageForEdit!!.messageId,
                    content = message.value!!,
                    channelArn = currentChannelArn
                ).onSuccess {
                    view.isEnabled = true
                    view.alpha = 1f
                    messageForEdit = null
                    _editMode.value = false
                    clearMessage()
                }.onFailure {
                    view.isEnabled = true
                    view.alpha = 1f
                    _showToast.value = R.string.edit_failed
                }
            }
        }
    }

    private fun clearMessage() {
        message.value = ""
    }

    fun cancelEdit(){
        messageForEdit = null
        _editMode.value = false
        message.value = ""
    }

    fun createNewMeeting() {
        showProgressBar()
        viewModelScope.launch {
            createMeetingUseCase.create().onSuccess {
                hideProgressBar()
                sendInvitationUseCase.send(it.meetingId, currentChannelArn)
            }.onFailure {
                hideProgressBar()
                _showToast.value = R.string.creation_failed
            }
        }
    }

    fun toggleEmojis(){
        _emojisOpened.value = !(emojisOpened.value ?: false)
        _toggleEmojisView.call()
    }

    private fun showProgressBar() {
        _showProgressBar.value = true
    }
    private fun hideProgressBar() {
        _showProgressBar.value = false
    }
}