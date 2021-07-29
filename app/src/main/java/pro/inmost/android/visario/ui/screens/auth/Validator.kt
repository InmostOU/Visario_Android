package pro.inmost.android.visario.ui.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pro.inmost.android.visario.R
import pro.inmost.android.visario.core.data.chimeapi.auth.model.RegisterRequestBody
import pro.inmost.android.visario.ui.utils.isValidEmail

class Validator {
    private val _emailInvalid = MutableLiveData<Int>()
    private val _usernameInvalid = MutableLiveData<Int>()
    private val _firstNameInvalid = MutableLiveData<Int>()
    private val _lastNameInvalid = MutableLiveData<Int>()
    private val _birthdayInvalid = MutableLiveData<Int>()
    private val _passInvalid = MutableLiveData<Int>()
    private val _confirmPassInvalid = MutableLiveData<Int>()

    val confirmPassInvalid: LiveData<Int> = _confirmPassInvalid
    val emailInvalid: LiveData<Int> = _emailInvalid
    val usernameInvalid: LiveData<Int> = _usernameInvalid
    val firstNameInvalid: LiveData<Int> = _firstNameInvalid
    val lastNameInvalid: LiveData<Int> = _lastNameInvalid
    val birthdayInvalid: LiveData<Int> = _birthdayInvalid
    val passInvalid: LiveData<Int> = _passInvalid

    fun validate(body: RegisterRequestBody): Boolean {
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

    fun validate(email: String?, pass: String?): Boolean {
        var valid = true
        if (!email.isValidEmail()) {
            _emailInvalid.value = R.string.invalid_email
            valid = false
        }
        if (pass.isNullOrBlank()) {
            _passInvalid.value = R.string.empty_field
            valid = false
        }

        return valid
    }
}