package pro.inmost.android.visario.ui.screens.contacts.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.domain.usecases.contacts.AddContactUseCase
import pro.inmost.android.visario.domain.usecases.contacts.UpdateContactUseCase
import pro.inmost.android.visario.ui.entities.contact.ContactUI
import pro.inmost.android.visario.ui.entities.contact.toDomainContact
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class EditContactViewModel(
    private val updateUseCase: UpdateContactUseCase,
    private val addUseCase: AddContactUseCase
) : ViewModel() {
    private val _contact = MutableLiveData<ContactUI>()
    val contact: LiveData<ContactUI> = _contact
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    private val _navigateBack = SingleLiveEvent<Unit>()
    val navigateBack: LiveData<Unit> = _navigateBack

    fun loadContact(contact: ContactUI){
        _contact.value = contact
        firstName.value = contact.firstName
        lastName.value = contact.lastName
    }

    fun saveContact(){
        viewModelScope.launch {
            contact.value?.let { contact ->
                firstName.value?.let { name ->
                    contact.firstName = name
                    contact.lastName = lastName.value ?: ""
                    if (contact.inMyContacts){
                        updateUseCase.update(contact.toDomainContact())
                    } else {
                        addUseCase.add(contact.username).onSuccess {
                            updateUseCase.update(contact.toDomainContact())
                        }
                    }
                }
            }
        }.invokeOnCompletion {
            _navigateBack.call()
        }
    }
}