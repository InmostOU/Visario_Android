package pro.inmost.android.visario.ui.dialogs.inviter.channel

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.usecases.channels.AddMemberToChannelUseCase
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelMembersUseCase
import pro.inmost.android.visario.domain.usecases.contacts.FetchContactsUseCase
import pro.inmost.android.visario.ui.base.BaseViewModel
import pro.inmost.android.visario.ui.entities.contact.ContactUI
import pro.inmost.android.visario.ui.entities.contact.toUIContacts
import pro.inmost.android.visario.ui.utils.extensions.replaceAll

class ChannelInviterViewModel(
    private val fetchContactsUseCase: FetchContactsUseCase,
    private val fetchChannelMembersUseCase: FetchChannelMembersUseCase,
    private val addMemberToChannelUseCase: AddMemberToChannelUseCase
) : BaseViewModel() {
    private var currentChannelArn: String = ""
    private val contacts = mutableListOf<ContactUI>()

    fun loadContacts() = fetchContactsUseCase.observe()
        .map {
            contacts.replaceAll(it.toUIContacts())
        }
        .asLiveData()

    fun inviteContact(contact: ContactUI){
        contact.disabled.set(true)
        viewModelScope.launch {
            addMemberToChannelUseCase.invite(currentChannelArn, contact.url).onSuccess {
                contact.invited.set(true)
            }.onFailure {
                contact.disabled.set(false)
                sendNotification(R.string.invitation_failed)
            }
        }
    }

    fun markContactsThatInvited(channelArn: String) {
        currentChannelArn = channelArn
        viewModelScope.launch {
            fetchChannelMembersUseCase.fetch(channelArn).map { it.toUIContacts() }.collect { members ->
                while (contacts.isEmpty()) delay(100)

                members.forEach { member ->
                    contacts.find { it.id == member.id || it.fullName == member.fullName }?.let {
                        it.invited.set(true)
                        it.disabled.set(true)
                    }
                }
            }
        }
    }
}