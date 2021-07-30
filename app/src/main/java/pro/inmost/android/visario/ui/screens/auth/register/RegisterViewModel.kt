package pro.inmost.android.visario.ui.screens.auth.register

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.R
import pro.inmost.android.visario.data.network.chimeapi.auth.model.RegisterRequest
import pro.inmost.android.visario.domain.usecases.AccountUseCase
import pro.inmost.android.visario.ui.screens.auth.Validator
import pro.inmost.android.visario.ui.utils.*

class RegisterViewModel(private val accountUseCase: AccountUseCase) : ViewModel() {
    val validator = Validator()
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val birthday = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    private val _backToLogin = SingleLiveEvent<Unit>()
    private val _showInfoDialogAndQuit = SingleLiveEvent<Unit>()

    val backToLogin: LiveData<Unit> = _backToLogin
    val showInfoDialogAndQuit: LiveData<Unit> = _showInfoDialogAndQuit

    fun register(view: View) {
        view.hideKeyboard()
        val request = createRegisterRequest()
        if (!validator.validate(request)) return

        view.isEnabled = false

        viewModelScope.launch {
            withContext(IO) {
                accountUseCase.register(request)
            }.onSuccess {
                _showInfoDialogAndQuit.call()
            }.onFailure {
                val message = it.message ?: view.context.getString(R.string.unknown_error)
                view.snackbar(message)
            }
            view.isEnabled = true
        }

    }

    private fun createRegisterRequest(): RegisterRequest {
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