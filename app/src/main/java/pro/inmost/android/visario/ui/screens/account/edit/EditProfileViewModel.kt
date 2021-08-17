package pro.inmost.android.visario.ui.screens.account.edit

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.domain.usecases.profile.FetchProfileUseCase
import pro.inmost.android.visario.domain.usecases.profile.UpdateProfileUseCase
import pro.inmost.android.visario.ui.entities.profile.ProfileUI
import pro.inmost.android.visario.ui.entities.profile.toDomainProfile
import pro.inmost.android.visario.ui.entities.profile.toUIProfile
import pro.inmost.android.visario.ui.utils.DateParser
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class EditProfileViewModel(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val fetchProfileUseCase: FetchProfileUseCase
) : ViewModel() {
    private var profile: ProfileUI? = null

    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val about = MutableLiveData<String>()
    val birthdate = MutableLiveData<String>()

    val dataValid = MediatorLiveData<Boolean>().apply {
        var firstNameValid = true
        var usernameValid = true
        var birthdayValid = true
        fun updateValue(){
            value = firstNameValid && usernameValid && birthdayValid
        }
        addSource(firstName){
            firstNameValid = it.isNotBlank()
            updateValue()
        }
        addSource(username){
            usernameValid = it.isNotBlank()
            updateValue()
        }
        addSource(birthdate){
            birthdayValid = it.isNotBlank()
            updateValue()
        }
    }


    val quitEvent = SingleLiveEvent<Unit>()

    init { loadProfile() }

    private fun loadProfile() {
        viewModelScope.launch {
            fetchProfileUseCase.fetch().onSuccess { result ->
                profile = result.toUIProfile().also {
                    firstName.value = it.firstName
                    lastName.value = it.lastName
                    username.value = it.username
                    about.value = it.about
                    birthdate.value = it.birthdateFormat
                }
            }
        }
    }

    fun updateProfile() {
        createUpdatedProfile()?.toDomainProfile()?.let { profile ->
            viewModelScope.launch {
                updateProfileUseCase.updateInfo(profile).onSuccess { status ->
                    quitEvent.call()
                }
            }
        }
    }

    private fun createUpdatedProfile() = profile?.copy(
        firstName = firstName.value ?: "",
        lastName = lastName.value ?: "",
        username = username.value ?: "",
        about = about.value ?: "",
        birthdate = DateParser.parseToMillis(birthdate.value ?: ""),
    )
}