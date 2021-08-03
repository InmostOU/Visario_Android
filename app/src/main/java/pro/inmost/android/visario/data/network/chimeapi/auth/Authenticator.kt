package pro.inmost.android.visario.data.network.chimeapi.auth

import pro.inmost.android.visario.data.network.chimeapi.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.network.chimeapi.auth.model.AuthResponse
import pro.inmost.android.visario.data.network.chimeapi.auth.model.LoginRequest
import pro.inmost.android.visario.data.network.chimeapi.auth.model.RegistrationRequest
import pro.inmost.android.visario.data.network.chimeapi.services.AccountService

class Authenticator(private val service: AccountService) {

    suspend fun login(email: String, password: String): AuthResponse {
        val request = LoginRequest(email, password)
        return service.login(request)
    }

    fun logout(): Boolean {
        clearTokens()
        return true
    }

    suspend fun register(body: RegistrationRequest): AuthResponse {
        return service.register(body)
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