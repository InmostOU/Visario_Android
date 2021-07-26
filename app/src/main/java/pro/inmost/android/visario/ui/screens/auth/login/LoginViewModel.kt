package pro.inmost.android.visario.ui.screens.auth.login

import android.content.Context
import android.view.View
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.model.entities.LoginRequest
import pro.inmost.android.visario.domain.model.entities.AuthResponse
import pro.inmost.android.visario.domain.model.entities.Tokens
import pro.inmost.android.visario.domain.utils.log
import pro.inmost.android.visario.ui.activities.dataStore
import pro.inmost.android.visario.ui.screens.auth.Authenticator
import pro.inmost.android.visario.ui.utils.*


class LoginViewModel(private val authenticator: Authenticator) : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _emailInvalid = MutableLiveData<Int>()
    val emailInvalid: LiveData<Int> = _emailInvalid

    private val _passInvalid = MutableLiveData<Int>()
    val passInvalid: LiveData<Int> = _passInvalid

    private val _openRegisterScreen = SingleLiveEvent<Unit>()
    val openRegisterScreen: LiveData<Unit> = _openRegisterScreen

    fun openRegisterScreen() {
        _openRegisterScreen.call()
    }

    fun login(view: View) {
        view.isEnabled = false

        viewModelScope.launch {
            val loginRequest = getLoginRequest()
            if (validate(loginRequest)) {
                when (val response = authenticator.login(loginRequest)) {
                    is AuthResponse.OK -> {
                        log("login ok: ${response.tokens}")
                        saveTokens(view.context, response.tokens)
                    }
                    is AuthResponse.Error -> {
                        log("login error: ${response.message}")
                        view.snackbar(response.message)
                    }
                }
            }
            view.isEnabled = true
        }
    }

    private fun saveTokens(context: Context, tokens: Tokens?) {
        viewModelScope.launch {
            context.dataStore.edit { pref ->
                pref[stringPreferencesKey(PREF_KEY_ACCESS_TOKEN)] = tokens?.accessToken ?: ""
                pref[stringPreferencesKey(PREF_KEY_REFRESH_TOKEN)] = tokens?.refreshToken ?: ""
            }
        }
    }

    private fun validate(request: LoginRequest): Boolean {
        var valid = true
        if (!request.email.isValidEmail()) {
            _emailInvalid.value = R.string.invalid_email
            valid = false
        }
        if (request.password.isBlank()) {
            _passInvalid.value = R.string.empty_field
            valid = false
        }

        return valid
    }

    private fun getLoginRequest(): LoginRequest {
        return LoginRequest(
            email = email.value ?: "",
            password = password.value ?: ""
        )
    }
}