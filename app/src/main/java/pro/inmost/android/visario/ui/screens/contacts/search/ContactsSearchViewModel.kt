package pro.inmost.android.visario.ui.screens.contacts.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.domain.usecases.contacts.AddContactUseCase
import pro.inmost.android.visario.domain.usecases.contacts.SearchContactsUseCase
import pro.inmost.android.visario.ui.entities.ContactUI
import pro.inmost.android.visario.ui.entities.toUIContacts
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class ContactsSearchViewModel(
    private val searchUseCase: SearchContactsUseCase,
) : ViewModel() {
    private val _openContactEvent = SingleLiveEvent<ContactUI>()
    val openContactEvent: LiveData<ContactUI> = _openContactEvent

    private val _editContactEvent = SingleLiveEvent<ContactUI>()
    val editContactEvent: LiveData<ContactUI> = _editContactEvent

    private val _contacts = MutableLiveData<List<ContactUI>>()
    val contacts: LiveData<List<ContactUI>> = _contacts


    fun onItemClick(item: ContactUI) {
        _openContactEvent.value = item
    }

    fun addContact(contact: ContactUI) {
        _editContactEvent.value = contact
    }

    fun search(username: String) {
        viewModelScope.launch {
            _contacts.value = searchUseCase.searchGlobal(username).toUIContacts()
        }
    }
}