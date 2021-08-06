package pro.inmost.android.visario.ui.screens.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import pro.inmost.android.visario.domain.entities.Contact
import pro.inmost.android.visario.domain.usecases.contacts.FetchContactsUseCase
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class ContactsViewModel(private val fetchContactsUseCase: FetchContactsUseCase) : ViewModel() {
    private val _openContactEvent = SingleLiveEvent<Int>()
    val openContactEvent: LiveData<Int> = _openContactEvent

    fun observeContacts() = fetchContactsUseCase.fetchAll().asLiveData()

    fun onItemClick(item: Contact){
        _openContactEvent.value = item.id
    }
}