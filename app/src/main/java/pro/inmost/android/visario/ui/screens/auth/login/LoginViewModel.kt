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
import pro.inmost.android.visario.core.data.chimeapi.auth.model.Tokens
import pro.inmost.android.visario.core.domain.entities.RequestResult
import pro.inmost.android.visario.ui.main.dataStore
import pro.inmost.android.visario.ui.boundaries.AccountRepository
import pro.inmost.android.visario.ui.utils.*


class LoginViewModel(private val repository: AccountRepository<RequestResult>) : ViewModel() {
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
        view.hideKeyboard()
        if (!validate()) return

        view.isEnabled = false

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.login(email.value!!, password.value!!)
                }

                when (response) {
                    is RequestResult.OK<*> -> {
                        saveTokens(view.context, response.data as Tokens?)
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

    private fun saveTokens(context: Context, tokens: Tokens?) {
        viewModelScope.launch {
            context.dataStore.edit { pref ->
                pref[stringPreferencesKey(PREF_KEY_ACCESS_TOKEN)] = tokens?.accessToken ?: ""
                pref[stringPreferencesKey(PREF_KEY_REFRESH_TOKEN)] = tokens?.refreshToken ?: ""
            }
        }
    }

    private fun validate(): Boolean {
        var valid = true
        if (!email.value.isValidEmail()) {
            _emailInvalid.value = R.string.invalid_email
            valid = false
        }
        if (password.value.isNullOrBlank()) {
            _passInvalid.value = R.string.empty_field
            valid = false
        }

        return valid
    }
}