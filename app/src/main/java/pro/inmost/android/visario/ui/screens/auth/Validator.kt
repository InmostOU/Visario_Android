package pro.inmost.android.visario.ui.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.entities.user.Register
import pro.inmost.android.visario.utils.extensions.isValidEmail


/**
 * Login and register fields validator
 *
 */
class Validator {
    private val maxLength = 100
    private val passRegex = "^(?=.[a-zA-Z])(?=.[0-9])(?=.*[^a-zA-Z0-9\\s]).{6,}"
    private val atLeastOneCharReg = "(?=.*[a-zA-Z])"             // At least one character in [a-zA-Z]
    private val atLeastOneDigReg = "(?=.*[0-9])"                // At least one digit.
    private val atLeastOneSpecialReg = "(?=.*[^a-zA-Z0-9\\s])" // At least one character that's not in [a-zA-Z0-9\s]
    private val min6CharLength = ".{6,}"                      // At least 6 characters.

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

    fun validate(register: Register): Boolean {
        var valid = true

        register.apply {
            if (!email.isValidEmail()) {
                _emailInvalid.value = R.string.invalid_email
                valid = false
            }

            if (username.isBlank()) {
                _usernameInvalid.value = R.string.empty_field
                valid = false
            } else if(username.length > maxLength) {
                _usernameInvalid.value = R.string.long_field
                valid = false
            }

            if (firstName.isBlank()) {
                _firstNameInvalid.value = R.string.empty_field
                valid = false
            } else if(firstName.length > maxLength) {
                _firstNameInvalid.value = R.string.long_field
                valid = false
            }

            if(lastName.length > maxLength) {
                _lastNameInvalid.value = R.string.long_field
                valid = false
            }

            valid = validatePassword(password, matchingPassword)

        }
        return valid
    }

    fun validatePassword(password: String, matchingPassword: String): Boolean{
        var valid = true
        if (!password.contains(min6CharLength.toRegex())) {
            _passInvalid.value = R.string.short_pass
            valid = false
        } else if (!password.contains(atLeastOneCharReg.toRegex())) {
            _passInvalid.value = R.string.at_least_one_char
            valid = false
        } else if (!password.contains(atLeastOneDigReg.toRegex())) {
            _passInvalid.value = R.string.at_least_one_dig
            valid = false
        } else if (!password.contains(atLeastOneSpecialReg.toRegex())) {
            _passInvalid.value = R.string.at_least_one_special
            valid = false
        }

        if (matchingPassword != password) {
            _confirmPassInvalid.value = R.string.password_not_confirmed
            valid = false
        }

        return valid
    }

    fun validate(email: String?, pass: String?): Boolean {
        var valid = validateEmail(email)
        if (pass.isNullOrBlank()) {
            _passInvalid.value = R.string.empty_field
            valid = false
        }

        return valid
    }

    fun validateEmail(email: String?): Boolean{
        var valid = true
        if (!email.isValidEmail()) {
            _emailInvalid.value = R.string.invalid_email
            valid = false
        }
        return valid
    }
}