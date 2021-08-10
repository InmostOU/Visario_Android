package pro.inmost.android.visario.data.api.services.auth

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.api.dto.requests.LoginRequest
import pro.inmost.android.visario.data.api.dto.requests.RegistrationRequest
import pro.inmost.android.visario.data.api.dto.responses.AuthResponse
import pro.inmost.android.visario.data.api.dto.responses.Tokens

class Authenticator(private val service: AuthService) {

    suspend fun login(email: String, password: String) = withContext(IO) {
        kotlin.runCatching {
            val response = service.login(LoginRequest(email, password))
            if (response.status == STATUS_OK) {
                Result.success(Tokens(response.accessToken, response.refreshToken))
            } else Result.failure(Throwable(response.message))
        }.getOrElse {  Result.failure(Throwable(it)) }
    }

    fun logout(): Boolean {
        clearTokens()
        return true
    }

    suspend fun register(body: RegistrationRequest) = withContext(IO){
        kotlin.runCatching {
            val response = service.register(body)
            if (response.status == STATUS_OK) {
                Result.success(Unit)
            } else Result.failure(Throwable(response.message))
        }.getOrElse {  Result.failure(Throwable(it)) }
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        TokensHolder.accessToken = accessToken
        TokensHolder.refreshToken = refreshToken
    }

    fun clearTokens(){
        TokensHolder.accessToken = ""
        TokensHolder.refreshToken = ""
    }

    object TokensHolder{
        var accessToken: String = ""
        var refreshToken: String = ""
    }
}