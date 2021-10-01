package pro.inmost.android.visario.ui.screens.channels.messages

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.entities.message.SendingMessage
import pro.inmost.android.visario.domain.usecases.channels.AddMemberToChannelUseCase
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCase
import pro.inmost.android.visario.domain.usecases.channels.LeaveChannelUseCase
import pro.inmost.android.visario.domain.usecases.meetings.CreateMeetingUseCase
import pro.inmost.android.visario.domain.usecases.meetings.SendInvitationUseCase
import pro.inmost.android.visario.domain.usecases.messages.*
import pro.inmost.android.visario.domain.usecases.profile.FetchProfileUseCase
import pro.inmost.android.visario.ui.base.BaseViewModel
import pro.inmost.android.visario.ui.entities.channel.ChannelUI
import pro.inmost.android.visario.ui.entities.channel.toUIChannel
import pro.inmost.android.visario.ui.entities.message.AttachmentUI
import pro.inmost.android.visario.ui.entities.message.AttachmentUI.FileType.IMAGE
import pro.inmost.android.visario.ui.entities.message.AttachmentUI.FileType.OTHER
import pro.inmost.android.visario.ui.entities.message.MessageUI
import pro.inmost.android.visario.ui.entities.message.toDomainAttachment
import pro.inmost.android.visario.ui.entities.message.toUIMessages
import pro.inmost.android.visario.ui.entities.profile.ProfileUI
import pro.inmost.android.visario.ui.entities.profile.toUIProfile
import pro.inmost.android.visario.ui.utils.FilesManager
import pro.inmost.android.visario.utils.SingleLiveEvent
import pro.inmost.android.visario.utils.extensions.meetingId
import pro.inmost.android.visario.utils.extensions.toAbsolutePath
import java.io.File

class MessagesViewModel(
    private val fetchMessagesUseCase: FetchMessagesUseCase,
    private val fetchChannelsUseCase: FetchChannelsUseCase,
    private val fetchProfileUseCase: FetchProfileUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val editMessageUseCase: EditMessageUseCase,
    private val deleteMessageUseCase: DeleteMessageUseCase,
    private val leaveChannelUseCase: LeaveChannelUseCase,
    private val updateReadStatusUseCase: UpdateMessagesReadStatusUseCase,
    private val addMemberToChannelUseCase: AddMemberToChannelUseCase,
    private val createMeetingUseCase: CreateMeetingUseCase,
    private val sendInvitationUseCase: SendInvitationUseCase
) : BaseViewModel() {
    private var profile: ProfileUI? = null
    var currentChannelArn: String = ""
        private set
    val message = MutableLiveData<String>()
    val attachment = MutableLiveData<AttachmentUI?>(null)

    private val currentChannel = MutableLiveData<ChannelUI>()

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

    private val _emojisOpened = MutableLiveData(false)
    val emojisOpened: LiveData<Boolean> = _emojisOpened

    private val _openPopupMenu = SingleLiveEvent<Pair<View, MessageUI>>()
    val openPopupMenu: LiveData<Pair<View, MessageUI>> = _openPopupMenu

    private val _openChannelInfoEvent = SingleLiveEvent<String>()
    val openChannelInfoEvent: LiveData<String> = _openChannelInfoEvent

    private val _openAttachmentMenu = SingleLiveEvent<Unit>()
    val openAttachmentMenu: LiveData<Unit> = _openAttachmentMenu

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

    fun joinChannel(view: View) {
        view.isEnabled = false
        viewModelScope.launch {
            profile?.let {
                addMemberToChannelUseCase.invite(currentChannelArn, it.userArn)
                    .map { it.toUIChannel() }
                    .onSuccess {
                        view.isEnabled = true
                        currentChannel.value = it
                    }.onFailure {
                        view.isEnabled = true
                    }
            }
        }
    }

    fun observeMessages(channelUrl: String): LiveData<List<MessageUI>> {
        return fetchMessagesUseCase.fetch(channelUrl).map { it.toUIMessages() }.asLiveData()
    }

    fun loadChannel(channelArn: String): LiveData<ChannelUI> {
        currentChannelArn = channelArn
        viewModelScope.launch {
            fetchChannelsUseCase.getChannel(channelArn)
                .map { it.toUIChannel() }
                .onSuccess {
                    currentChannel.value = it
                }
        }
        return currentChannel
    }

    fun onAttachFileClick() {
        _openAttachmentMenu.call()
    }

    fun sendMessage() {
        if (message.value.isNullOrBlank() && attachment.value == null) return

        val message = createMessage()
        clearMessage()
        viewModelScope.launch {
            sendMessageUseCase.send(message)
        }
    }

    private fun createMessage(): SendingMessage {
        val textMessage = message.value?.trim() ?: ""
        val attachment = attachment.value?.toDomainAttachment()
        return SendingMessage(currentChannelArn, textMessage, attachment)
    }

    fun resendMessage(message: MessageUI) {
        viewModelScope.launch {
            sendMessageUseCase.resend(message.awsId)
        }
    }

    fun leaveChannel() {
        viewModelScope.launch {
            leaveChannelUseCase.leave(currentChannelArn).onSuccess {
                _closeChannel.call()
            }
        }
    }

    fun updateReadStatus() {
        viewModelScope.launch {
            updateReadStatusUseCase.updateAll(currentChannelArn, true)
        }
    }

    fun onMessageClick(view: View, message: MessageUI) {
        _openPopupMenu.value = view to message
    }

    fun onInvitationClick(message: MessageUI) {
        if (message.isMeetingInvitation) {
            message.text.meetingId?.let {
                _joinMeetingEvent.value = it
            }
        }
    }

    fun onChannelClick() {
        _openChannelInfoEvent.value = currentChannelArn
    }

    fun editMessage(message: MessageUI) {
        _editMode.value = true
        messageForEdit = message
        this.message.value = message.text
        _showKeyboard.call()
    }

    fun deleteLocalMessage(message: MessageUI) {
        viewModelScope.launch {
            deleteMessageUseCase.deleteLocal(message.awsId)
        }
    }

    fun deleteMessage(message: MessageUI) {
        viewModelScope.launch {
            deleteMessageUseCase.delete(message.awsId)
        }
    }

    fun editDone(view: View) {
        view.isEnabled = false
        view.alpha = 0.5f
        viewModelScope.launch {
            if (messageForEdit != null && message.value != null) {
                editMessageUseCase.edit(
                    messageId = messageForEdit!!.awsId,
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
                    sendNotification(R.string.edit_failed)
                }
            }
        }
    }

    private fun clearMessage() {
        message.value = ""
        attachment.value = null
    }

    fun cancelEdit() {
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
                sendNotification(R.string.creation_failed)
            }
        }
    }

    fun toggleEmojis() {
        _emojisOpened.value = !(emojisOpened.value ?: false)
        _toggleEmojisView.call()
    }

    private fun showProgressBar() {
        _showProgressBar.value = true
    }

    private fun hideProgressBar() {
        _showProgressBar.value = false
    }

    fun attachImage(context: Context, uri: Uri) {
        val path = uri.toAbsolutePath(context)
        val extension = path.substringAfterLast(".", "")
        attachment.value = AttachmentUI(
            path = path,
            name = File(path).name,
            type = IMAGE,
            extension = extension
        )
    }

    fun detachFile() {
        attachment.value = null
    }

    fun attachFile(context: Context, uri: Uri) {
        FilesManager.saveToCache(context, uri)?.let { path ->
            val extension = path.substringAfterLast(".", "")
            attachment.value = AttachmentUI(
                path = path,
                name = File(path).name,
                type = OTHER,
                extension = extension
            )
        }

    }

    fun downloadAttachment(context: Context, message: MessageUI) {
        viewModelScope.launch {
            message.attachment?.let { attachment ->
                FilesManager.saveInDownloads(
                    context,
                    attachment.path,
                    attachment.name,
                    attachment.extension
                )
            }
        }
    }
}

