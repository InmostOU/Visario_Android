package pro.inmost.android.visario.ui.screens.contacts

import androidx.lifecycle.*
import pro.inmost.android.visario.domain.usecases.contacts.FetchContactsUseCase
import pro.inmost.android.visario.domain.usecases.contacts.SearchContactsUseCase
import pro.inmost.android.visario.ui.entities.contact.ContactUI
import pro.inmost.android.visario.ui.entities.contact.toUIContacts
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class ContactsViewModel(
    private val fetchContactsUseCase: FetchContactsUseCase,
    private val searchContactsUseCase: SearchContactsUseCase
) : ViewModel() {
    private val _openContactEvent = SingleLiveEvent<ContactUI>()
    val openContactEvent: LiveData<ContactUI> = _openContactEvent

    val contacts: LiveData<List<ContactUI>>
        get() = fetchContactsUseCase.observe().asLiveData().map { it.toUIContacts() }


    fun onItemClick(item: ContactUI){
        _openContactEvent.value = item
    }

    suspend fun search(query: String): List<ContactUI> {
        return if (query.isBlank()){
            fetchContactsUseCase.fetchAll()
        } else {
            searchContactsUseCase.searchLocal(query)
        }.toUIContacts()
    }
}