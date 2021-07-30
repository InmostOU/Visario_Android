package pro.inmost.android.visario.data.repositories

import pro.inmost.android.visario.data.network.chimeapi.ChimeApi
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.network.chimeapi.auth.model.RegisterRequest
import pro.inmost.android.visario.data.network.chimeapi.auth.model.Tokens
import pro.inmost.android.visario.domain.boundaries.AccountRepository

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

    override suspend fun register(body: RegisterRequest): Result<String> {
        return try {
            val response = chimeApi.auth.register(body)
            if (response.status == STATUS_OK) {
                Result.success(response.message)
            } else Result.failure(Throwable(response.message))
        } catch (e: Throwable){
            Result.failure(Throwable(e.message))
        }
    }
}