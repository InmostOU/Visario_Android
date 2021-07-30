package pro.inmost.android.visario.ui.screens.auth.login

import android.content.Context
import android.view.View
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.R
import pro.inmost.android.visario.data.network.chimeapi.auth.model.Tokens
import pro.inmost.android.visario.ui.main.dataStore
import pro.inmost.android.visario.domain.boundaries.AccountRepository
import pro.inmost.android.visario.domain.usecases.AccountUseCase
import pro.inmost.android.visario.ui.screens.auth.Validator
import pro.inmost.android.visario.ui.utils.*


class LoginViewModel(private val accountUseCase: AccountUseCase) : ViewModel() {
    val validator = Validator()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _openRegisterScreen = SingleLiveEvent<Unit>()
    val openRegisterScreen: LiveData<Unit> = _openRegisterScreen

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
                accountUseCase.login(email.value!!, password.value!!)
            }.onSuccess {
                saveTokens(view.context, it)
            }.onFailure {
                val message = it.message ?: view.context.getString(R.string.unknown_error)
                view.snackbar(message)
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
}