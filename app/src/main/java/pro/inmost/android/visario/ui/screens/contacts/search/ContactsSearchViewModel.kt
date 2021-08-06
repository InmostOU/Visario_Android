package pro.inmost.android.visario.ui.screens.contacts.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pro.inmost.android.visario.domain.entities.Contact
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class ContactsSearchViewModel : ViewModel() {
    private val _openContactEvent = SingleLiveEvent<Int>()
    val openContactEvent: LiveData<Int> = _openContactEvent


    fun onItemClick(item: Contact){
        _openContactEvent.value = item.id
    }

    fun addContact(contact: Contact){

    }

    fun search(username: String){

    }

}