package pro.inmost.android.visario.ui.screens.auth.register

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.R
import pro.inmost.android.visario.core.data.chimeapi.auth.model.RegisterBody
import pro.inmost.android.visario.core.domain.entities.RequestResult
import pro.inmost.android.visario.ui.boundaries.AccountRepository
import pro.inmost.android.visario.ui.utils.*

class RegisterViewModel(private val repository: AccountRepository<RequestResult>) : ViewModel() {
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
        if (!validate(request)) return

        view.isEnabled = false

        viewModelScope.launch {
            try {
                val response = withContext(IO) { repository.register(request) }
                when (response) {
                    is RequestResult.OK<*> -> {
                        _showInfoDialogAndQuit.call()
                    }
                    is RequestResult.Error -> {
                        view.snackbar(response.message)
                    }
                }
                view.isEnabled = true
            } catch (e: Exception) {
                e.printStackTrace()
                view.snackbar(e.localizedMessage ?: "Error")
                view.isEnabled = true
            }
        }

    }

    private fun validate(body: RegisterBody): Boolean {
        var valid = true

        body.apply {
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

    private fun getRegisterRequest(): RegisterBody {
        return RegisterBody(
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