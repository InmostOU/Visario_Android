package pro.inmost.android.visario.ui.screens.contacts.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pro.inmost.android.visario.domain.entities.Contact
import pro.inmost.android.visario.domain.usecases.contacts.FetchContactsUseCase
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class ContactDetailViewModel(private val fetchContactsUseCase: FetchContactsUseCase) : ViewModel() {
    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact> = _contact


    fun loadContact(id: Int){
        viewModelScope.launch {
            fetchContactsUseCase.fetch(id).collect { _contact.value = it }
        }
    }

    fun startChatting(){

    }

    fun call(){

    }

    fun videoCall(){

    }

    fun addContact(){

    }
}