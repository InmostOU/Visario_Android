package pro.inmost.android.visario.data.repositories

import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.dto.requests.toRegistrationRequest
import pro.inmost.android.visario.data.database.AppDatabase
import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.domain.entities.user.Register
import pro.inmost.android.visario.domain.repositories.AccountRepository

class AccountRepositoryImpl(private val chimeApi: ChimeApi) : AccountRepository {

    override suspend fun login(email: String, password: String): Result<Credentials> {
        chimeApi.auth.login(email, password).onSuccess {
            val credentials = Credentials(
                currentUser = email,
                accessToken = it.accessToken,
                refreshToken = it.refreshToken)
            return Result.success(credentials)
        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(Throwable("Unknown error"))
    }

    override suspend fun logout(): Boolean {
        return chimeApi.auth.logout()
    }

    override suspend fun register(register: Register): Result<Unit> {
        return chimeApi.auth.register(register.toRegistrationRequest())
    }

    override fun updateCredentials(credentials: Credentials) {
        chimeApi.auth.saveTokens(credentials.accessToken, credentials.refreshToken)
        if (credentials.currentUser.isNotBlank()) {
            AppDatabase.updateName(credentials.currentUser)
        }
    }
}