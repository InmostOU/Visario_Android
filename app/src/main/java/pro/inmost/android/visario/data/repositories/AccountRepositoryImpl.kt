package pro.inmost.android.visario.data.repositories

import pro.inmost.android.visario.data.network.chimeapi.ChimeApi
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.network.chimeapi.auth.model.Tokens
import pro.inmost.android.visario.data.network.utils.toRegistrationRequest
import pro.inmost.android.visario.domain.repositories.AccountRepository
import pro.inmost.android.visario.domain.entities.User

class AccountRepositoryImpl(private val chimeApi: ChimeApi) : AccountRepository {

    override suspend fun login(email: String, password: String): Result<Tokens> {
        return try {
            val response = chimeApi.auth.login(email, password)
            if (response.status == STATUS_OK){
                Result.success(Tokens(response.accessToken, response.refreshToken))
            } else Result.failure(Throwable(response.message))
        } catch (e: Throwable){
            Result.failure(Throwable(e.message))
        }
    }

    override suspend fun logout(): Boolean {
        return chimeApi.auth.logout()
    }

    override suspend fun register(user: User): Result<String> {
        return try {
            val response = chimeApi.auth.register(user.toRegistrationRequest())
            if (response.status == STATUS_OK) {
                Result.success(response.message)
            } else Result.failure(Throwable(response.message))
        } catch (e: Throwable){
            Result.failure(Throwable(e.message))
        }
    }

    override fun updateTokens(tokens: Tokens) {
        chimeApi.auth.apply {
            accessToken = tokens.accessToken
            refreshToken = tokens.refreshToken
        }
    }
}