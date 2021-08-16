package pro.inmost.android.visario.ui.screens.auth.login

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.domain.usecases.auth.login.LoginUseCase
import pro.inmost.android.visario.ui.screens.auth.CredentialsStore
import pro.inmost.android.visario.ui.screens.auth.Validator
import pro.inmost.android.visario.ui.utils.SingleLiveEvent
import pro.inmost.android.visario.ui.utils.extensions.snackbar
import pro.inmost.android.visario.ui.utils.hideKeyboard
import pro.inmost.android.visario.ui.utils.log


class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val credentialsStore: CredentialsStore
) : ViewModel(){
    val validator = Validator()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _openRegisterScreen = SingleLiveEvent<Unit>()
    val openRegisterScreen: LiveData<Unit> = _openRegisterScreen

    private val _loginSuccessful = SingleLiveEvent<Unit>()
    val loginSuccessful: LiveData<Unit> = _loginSuccessful

    fun openRegisterScreen() {
        _openRegisterScreen.call()
    }

    fun login(view: View) {
        view.hideKeyboard()
        val valid = validator.validate(email.value, password.value)
        if (!valid) return

        view.isEnabled = false

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loginUseCase.login(email.value!!, password.value!!)
            }.onSuccess {
                updateCredentials(it)
                _loginSuccessful.call()
            }.onFailure {
                val message = it.message ?: view.context.getString(R.string.unknown_error)
                view.snackbar(message)
            }
            view.isEnabled = true
        }

    }

    private fun updateCredentials(credentials: Credentials) {
        log("creds: $credentials")
        credentialsStore.saveCredentials(credentials)
    }
}