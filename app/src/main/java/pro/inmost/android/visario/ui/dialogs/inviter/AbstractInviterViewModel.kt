package pro.inmost.android.visario.ui.dialogs.inviter

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.map
import pro.inmost.android.visario.domain.usecases.contacts.FetchContactsUseCase
import pro.inmost.android.visario.ui.entities.contact.ContactUI
import pro.inmost.android.visario.ui.entities.contact.toUIContacts

abstract class AbstractInviterViewModel(
    private val fetchContactsUseCase: FetchContactsUseCase,
) : ViewModel() {

    fun loadContacts() = fetchContactsUseCase.observe().map { it.toUIContacts() }.asLiveData()

    abstract fun inviteContact(view: View, contact: ContactUI)

}