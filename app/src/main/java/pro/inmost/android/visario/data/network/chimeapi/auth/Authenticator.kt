package pro.inmost.android.visario.data.network.chimeapi.auth

import pro.inmost.android.visario.data.network.chimeapi.ChimeApi
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.network.chimeapi.auth.model.*
import pro.inmost.android.visario.data.network.chimeapi.services.AccountService

class Authenticator(private val service: AccountService) {

    suspend fun login(email: String, password: String): AuthResponse {
        val request = LoginRequestBody(email, password)
        return service.login(request).apply {
            if (status == STATUS_OK && accessToken.isNotBlank()){
                saveTokens(accessToken, refreshToken)
            }
        }
    }

    fun logout(): Boolean {
        ChimeApi.TokensHolder.deleteTokens()
        return true
    }

    suspend fun register(body: RegisterRequest): AuthResponse {
        return service.register(body)
    }

    private fun saveTokens(accessToken: String, refreshToken: String) {
        ChimeApi.TokensHolder.updateTokens(
            Tokens(
                accessToken,
                refreshToken
            )
        )
    }
}