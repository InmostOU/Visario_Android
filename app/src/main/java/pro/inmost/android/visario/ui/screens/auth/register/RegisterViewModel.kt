package pro.inmost.android.visario.ui.screens.auth.register

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.R
import pro.inmost.android.visario.core.domain.entities.auth.AuthResponse
import pro.inmost.android.visario.core.domain.entities.auth.RegisterRequest
import pro.inmost.android.visario.core.domain.utils.log
import pro.inmost.android.visario.ui.screens.auth.Authenticator
import pro.inmost.android.visario.ui.utils.*

class RegisterViewModel(private val authenticator: Authenticator) : ViewModel() {
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val birthday = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    private val _emailInvalid = MutableLiveData<Int>()
    val emailInvalid: LiveData<Int> = _emailInvalid
    private val _usernameInvalid = MutableLiveData<Int>()
    val usernameInvalid: LiveData<Int> = _usernameInvalid
    private val _firstNameInvalid = MutableLiveData<Int>()
    val firstNameInvalid: LiveData<Int> = _firstNameInvalid
    private val _lastNameInvalid = MutableLiveData<Int>()
    val lastNameInvalid: LiveData<Int> = _lastNameInvalid
    private val _birthdayInvalid = MutableLiveData<Int>()
    val birthdayInvalid: LiveData<Int> = _birthdayInvalid
    private val _passInvalid = MutableLiveData<Int>()
    val passInvalid: LiveData<Int> = _passInvalid
    private val _confirmPassInvalid = MutableLiveData<Int>()
    val confirmPassInvalid: LiveData<Int> = _confirmPassInvalid

    private val _backToLogin = SingleLiveEvent<Unit>()
    val backToLogin: LiveData<Unit> = _backToLogin

    private val _showInfoDialogAndQuit = SingleLiveEvent<Unit>()
    val showInfoDialogAndQuit: LiveData<Unit> = _showInfoDialogAndQuit

    fun register(view: View) {
        view.hideKeyboard()
        val request = getRegisterRequest()
        if (validate(request)) {
            view.isEnabled = false
            viewModelScope.launch(Dispatchers.IO) {
                val response = authenticator.register(request)
                withContext(Dispatchers.Main) {
                    when (response) {
                        is AuthResponse.OK -> {
                            log("register ok")
                            _showInfoDialogAndQuit.call()
                        }
                        is AuthResponse.Error -> {
                            log("register error: ${response.message}")
                            view.snackbar(response.message)
                        }
                    }
                    view.isEnabled = true
                }
            }
        }
    }

    private fun validate(request: RegisterRequest): Boolean {
        var valid = true

        request.apply {
            if (!email.isValidEmail()) {
                _emailInvalid.value = R.string.invalid_email
                valid = false
            }
            if (username.isBlank()) {
                _usernameInvalid.value = R.string.empty_field
                valid = false
            }
            if (firstName.isBlank()) {
                _firstNameInvalid.value = R.string.empty_field
                valid = false
            }
            if (lastName.isBlank()) {
                _lastNameInvalid.value = R.string.empty_field
                valid = false
            }
            if (birthday == -1L) {
                _birthdayInvalid.value = R.string.invalid_date
                valid = false
            }
            if (password.isBlank()) {
                _passInvalid.value = R.string.empty_field
                valid = false
            }
            if (matchingPassword != password) {
                _confirmPassInvalid.value = R.string.password_not_confirmed
                valid = false
            }
        }
        return valid
    }

    private fun getRegisterRequest(): RegisterRequest {
        return RegisterRequest(
            username = username.value ?: "",
            email = email.value ?: "",
            firstName = firstName.value ?: "",
            lastName = lastName.value ?: "",
            birthday = DateParser.parseToMillis(birthday.value),
            password = password.value ?: "",
            matchingPassword = confirmPassword.value ?: ""
        )
    }

    fun openLoginScreen() {
        _backToLogin.call()
    }
}