package pro.inmost.android.visario.data.api.services.auth

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.api.dto.requests.auth.LoginRequest
import pro.inmost.android.visario.data.api.dto.requests.auth.RegistrationRequest
import pro.inmost.android.visario.data.api.dto.responses.auth.Tokens
import pro.inmost.android.visario.data.api.utils.logError

class Authenticator(private val service: AuthService) {

    suspend fun login(email: String, password: String) = withContext(IO) {
        kotlin.runCatching {
            val response = service.login(LoginRequest(email, password))
            if (response.status == STATUS_OK) {
                Result.success(Tokens(response.accessToken, response.refreshToken))
            } else {
                logError("login error: ${response.message}")
                Result.failure(Throwable(response.message))
            }
        }.getOrElse {
            logError("login error: ${it.message}")
            Result.failure(Throwable(it))
        }
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
            } else {
                logError("register error: ${response.message}")
                Result.failure(Throwable(response.message))
            }
        }.getOrElse {
            logError("register error: ${it.message}")
            Result.failure(Throwable(it))
        }
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