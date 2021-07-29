package pro.inmost.android.visario.core.data.chimeapi.auth

import pro.inmost.android.visario.core.data.chimeapi.ChimeApi
import pro.inmost.android.visario.core.data.chimeapi.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.core.data.chimeapi.auth.model.*
import pro.inmost.android.visario.core.data.chimeapi.services.AccountService

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

    suspend fun register(body: RegisterRequestBody): AuthResponse {
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