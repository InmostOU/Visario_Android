package pro.inmost.android.visario.data.network.chimeapi.auth

import pro.inmost.android.visario.data.network.chimeapi.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.network.chimeapi.auth.model.AuthResponse
import pro.inmost.android.visario.data.network.chimeapi.auth.model.LoginRequest
import pro.inmost.android.visario.data.network.chimeapi.auth.model.RegistrationRequest
import pro.inmost.android.visario.data.network.chimeapi.services.AccountService

class Authenticator(private val service: AccountService) {
    var accessToken: String = ""
    var refreshToken: String = ""

    suspend fun login(email: String, password: String): AuthResponse {
        val request = LoginRequest(email, password)
        return service.login(request).apply {
            if (status == STATUS_OK && accessToken.isNotBlank()){
                saveTokens(accessToken, refreshToken)
            }
        }
    }

    fun logout(): Boolean {
        clearTokens()
        return true
    }

    suspend fun register(body: RegistrationRequest): AuthResponse {
        return service.register(body)
    }

    private fun saveTokens(accessToken: String, refreshToken: String) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }

    fun clearTokens(){
        accessToken = ""
        refreshToken = ""
    }
}