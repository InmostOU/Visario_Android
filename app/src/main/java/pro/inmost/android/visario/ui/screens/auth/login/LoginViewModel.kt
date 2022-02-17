package pro.inmost.android.visario.ui.screens.auth.login

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.domain.usecases.auth.LoginUseCase
import pro.inmost.android.visario.ui.screens.auth.Validator
import pro.inmost.android.visario.utils.SingleLiveEvent
import pro.inmost.android.visario.utils.hideKeyboard


class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel(){
    val validator = Validator()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _openRegisterScreen = SingleLiveEvent<Unit>()
    val openRegisterScreen: LiveData<Unit> = _openRegisterScreen

    private val _showSnackbar = SingleLiveEvent<Int>()
    val showSnackbar: LiveData<Int> = _showSnackbar

    private val _loginSuccessful = SingleLiveEvent<Credentials>()
    val loginSuccessful: LiveData<Credentials> = _loginSuccessful

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun openRegisterScreen() {
        _openRegisterScreen.call()
    }

    fun login(view: View) {
        view.hideKeyboard()
        val valid = validator.validate(email.value, password.value)
        if (!valid) return

        showLoading(true)

        viewModelScope.launch {
            val result = loginUseCase.login(email.value!!, password.value!!)
            onLoginResult(result)
        }
    }

    fun loginViaFacebook(result: LoginResult?) {
        showLoading(true)
        result?.let {
            viewModelScope.launch {
                onLoginResult(
                    loginUseCase.loginViaFacebook(it.accessToken.token)
                )
            }
        }
    }

    fun loginViaGoogle(result: GoogleSignInAccount) {
        showLoading(true)
        viewModelScope.launch {
            onLoginResult(
                loginUseCase.loginViaGoogle(result.idToken ?: "")
            )
        }
    }

    private fun onLoginResult(result: Result<Credentials>){
        showLoading(false)
        result.onSuccess {
            _loginSuccessful.value = it
        }.onFailure {
            _showSnackbar.value = R.string.login_failed
        }
    }

    fun showLoading(show: Boolean){
        _loading.value = show
    }
}