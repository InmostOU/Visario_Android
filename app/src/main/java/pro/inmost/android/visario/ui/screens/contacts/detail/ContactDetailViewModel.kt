package pro.inmost.android.visario.ui.screens.contacts.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.domain.usecases.contacts.DeleteContactUseCase
import pro.inmost.android.visario.domain.usecases.contacts.FetchContactsUseCase
import pro.inmost.android.visario.ui.entities.contact.ContactUI
import pro.inmost.android.visario.ui.entities.contact.toUIContact
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class ContactDetailViewModel(
    private val fetchContactsUseCase: FetchContactsUseCase,
    private val deleteContactsUseCase: DeleteContactUseCase
) : ViewModel() {
    private val _contact = MutableLiveData<ContactUI>()
    val contact: LiveData<ContactUI> = _contact

    private val _openEditContactEvent = SingleLiveEvent<ContactUI>()
    val openEditContactEvent: LiveData<ContactUI> = _openEditContactEvent

    private val _closeFragmentEvent = SingleLiveEvent<ContactUI>()
    val closeFragmentEvent: LiveData<ContactUI> = _closeFragmentEvent


    fun loadContact(contact: ContactUI){
        _contact.value = contact
        viewModelScope.launch {
            fetchContactsUseCase.fetch(contact.username).onSuccess {
                _contact.value = it.toUIContact()
            }
        }
    }

    fun loadContact(username: String){
        viewModelScope.launch {
            fetchContactsUseCase.fetch(username).onSuccess {
                _contact.value = it.toUIContact()
            }
        }
    }

    fun startChatting(){

    }

    fun call(){

    }

    fun videoCall(){

    }

    fun sendMail(){

    }

    fun addContact() {
        _openEditContactEvent.value = contact.value
    }

    fun deleteContact(id: Long) {
        viewModelScope.launch {
            deleteContactsUseCase.delete(id).onSuccess {
                _closeFragmentEvent.call()
            }
        }
    }

    fun blockContact(username: String) {
        
    }
}