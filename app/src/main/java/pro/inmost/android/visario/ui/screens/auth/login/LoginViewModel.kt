package pro.inmost.android.visario.ui.screens.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.domain.usecases.auth.LoginUseCase
import pro.inmost.android.visario.ui.screens.auth.AppPreferences
import pro.inmost.android.visario.ui.screens.auth.Validator
import pro.inmost.android.visario.utils.SingleLiveEvent


class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val appPreferences: AppPreferences
) : ViewModel(){
    val validator = Validator()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val isLoggedInBefore: Boolean
        get() = appPreferences.isCredentialsNotEmpty

    private val _showSnackbar = SingleLiveEvent<Int>()
    val showSnackbar: LiveData<Int> = _showSnackbar

    private val _loginSuccessful = SingleLiveEvent<Credentials>()
    val loginSuccessful: LiveData<Credentials> = _loginSuccessful

    private val _loginButtonEnabled = MutableLiveData(true)
    val loginButtonEnabled: LiveData<Boolean> = _loginButtonEnabled

    fun login() {
        val valid = validator.validate(email.value, password.value)
        if (!valid) return

        _loginButtonEnabled.value = false

        viewModelScope.launch {
            loginUseCase.login(email.value!!, password.value!!).onSuccess { credentials ->
                appPreferences.saveCredentials(credentials)
                _loginSuccessful.value = credentials
            }.onFailure {
                _showSnackbar.value = R.string.login_failed
            }
            _loginButtonEnabled.value = true
        }
    }
}