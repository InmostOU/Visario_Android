package pro.inmost.android.visario.ui.dialogs.select.contacts

import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.usecases.channels.AddMemberToChannelUseCase
import pro.inmost.android.visario.domain.usecases.contacts.FetchContactsUseCase
import pro.inmost.android.visario.ui.entities.contact.ContactUI
import pro.inmost.android.visario.ui.entities.contact.toDomainContact
import pro.inmost.android.visario.ui.entities.contact.toUIContacts
import pro.inmost.android.visario.ui.utils.extensions.toast

class ContactsInviterViewModel(
    private val fetchContactsUseCase: FetchContactsUseCase,
    private val addMemberToChannelUseCase: AddMemberToChannelUseCase
) : ViewModel() {
    private var channelUrl: String = ""

    fun loadContacts() = fetchContactsUseCase.observe().map { it.toUIContacts() }.asLiveData()

    fun inviteContact(view: View,  contact: ContactUI){
        view.isEnabled = false
        viewModelScope.launch {
            addMemberToChannelUseCase.invite(channelUrl, contact.toDomainContact()).onSuccess {
                (view as Button).text = view.context.getString(R.string.invited)
            }.onFailure {
                view.isEnabled = true
                view.toast(R.string.invitation_failed)
            }
        }
    }

    fun setChannel(url: String) {
        channelUrl = url
    }
}