package pro.inmost.android.visario.ui.screens.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.inmost.android.visario.ui.screens.auth.Authenticator
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class RegisterViewModel(private val authenticator: Authenticator) : ViewModel() {
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val birthday = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()


    private val _backToLogin = SingleLiveEvent<Unit>()
    val backToLogin: LiveData<Unit> = _backToLogin

    fun register(){
        getRegisterRequest()
    }

    private fun getRegisterRequest() {

    }

    fun openLoginScreen(){
        _backToLogin.call()
    }
}