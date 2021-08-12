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
import pro.inmost.android.visario.ui.utils.ifNotEmpty

class ContactsSearchViewModel(
    private val searchUseCase: SearchContactsUseCase,
) : ViewModel() {
    private val _openContactEvent = SingleLiveEvent<ContactUI>()
    val openContactEvent: LiveData<ContactUI> = _openContactEvent

    private val _editContactEvent = SingleLiveEvent<ContactUI>()
    val editContactEvent: LiveData<ContactUI> = _editContactEvent

    private val _contacts = MutableLiveData<List<ContactUI>>()
    val contacts: LiveData<List<ContactUI>> = _contacts

    private var lastSearch = ""

    fun onItemClick(item: ContactUI) {
        _openContactEvent.value = item
    }

    fun addContact(contact: ContactUI) {
        _editContactEvent.value = contact
    }

    fun search(username: String) {
        lastSearch = username
        viewModelScope.launch {
            _contacts.value = searchUseCase.searchGlobal(username).toUIContacts()
        }
    }

    fun updateLastSearch() {
        lastSearch.ifNotEmpty {
            viewModelScope.launch {
                _contacts.value = searchUseCase.searchGlobal(lastSearch).toUIContacts()
            }
        }
    }
}