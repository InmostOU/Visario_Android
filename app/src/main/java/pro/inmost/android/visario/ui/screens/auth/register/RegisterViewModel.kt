package pro.inmost.android.visario.ui.screens.auth.register

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.entities.user.Register
import pro.inmost.android.visario.domain.usecases.auth.RegistrationUseCase
import pro.inmost.android.visario.ui.screens.auth.Validator
import pro.inmost.android.visario.ui.utils.SingleLiveEvent
import pro.inmost.android.visario.ui.utils.extensions.snackbar
import pro.inmost.android.visario.ui.utils.hideKeyboard

class RegisterViewModel(private val registrationUseCase: RegistrationUseCase) : ViewModel() {
    val validator = Validator()
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    private val _backToLogin = SingleLiveEvent<Unit>()
    private val _showInfoDialogAndQuit = SingleLiveEvent<Unit>()

    val backToLogin: LiveData<Unit> = _backToLogin
    val showInfoDialogAndQuit: LiveData<Unit> = _showInfoDialogAndQuit

    fun register(view: View) {
        view.hideKeyboard()
        val user = createUser()
        if (!validator.validate(user)) return

        view.isEnabled = false

        viewModelScope.launch {
            withContext(IO) {
                registrationUseCase.register(user)
            }.onSuccess {
                _showInfoDialogAndQuit.call()
            }.onFailure {
                view.snackbar(R.string.register_failed)
            }
            view.isEnabled = true
        }

    }

    private fun createUser(): Register {
        return Register(
            username = username.value ?: "",
            email = email.value ?: "",
            firstName = firstName.value ?: "",
            lastName = lastName.value ?: "",
            password = password.value ?: "",
            matchingPassword = confirmPassword.value ?: ""
        )
    }

    fun openLoginScreen() {
        _backToLogin.call()
    }
}