package pro.inmost.android.visario.ui.screens.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import pro.inmost.android.visario.domain.model.entities.LoginRequest
import pro.inmost.android.visario.domain.utils.log
import pro.inmost.android.visario.ui.screens.auth.Authenticator
import pro.inmost.android.visario.ui.utils.SingleLiveEvent


class LoginViewModel(private val authenticator: Authenticator) : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _openRegisterScreen = SingleLiveEvent<Unit>()
    val openRegisterScreen: LiveData<Unit> = _openRegisterScreen

    fun openRegisterScreen(){
        _openRegisterScreen.call()
    }

    fun login(){
        val loginRequest = getLoginRequest()
        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
        val json = Gson().toJson(loginRequest)
        val client = OkHttpClient()

        val body: RequestBody = RequestBody.create(JSON, json)
        val request: Request = Request.Builder()
            .url("http://3.129.6.178:8081/auth/login")
            .post(body)
            .build()
        viewModelScope.launch(Dispatchers.IO) {
            client.newCall(request).execute().use { response ->
                val body = response.body?.string() ?: ""
                log(body)
            }
        }

    }

    private fun getLoginRequest(): LoginRequest {
        return LoginRequest(
            email = email.value ?: "",
            password = password.value ?: ""
        )
    }
}