package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.dto.requests.toCredentials
import pro.inmost.android.visario.data.api.dto.requests.toRegistrationRequest
import pro.inmost.android.visario.data.database.AppDatabase
import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.domain.entities.user.Register
import pro.inmost.android.visario.domain.repositories.AccountRepository

class AccountRepositoryImpl(
    private val chimeApi: ChimeApi
) : AccountRepository {

    override suspend fun login(email: String, password: String): Result<Credentials> = withContext(IO) {
        chimeApi.auth.login(email, password).map { it.toCredentials() }
    }

    override suspend fun loginViaFacebook(token: String): Result<Credentials> = withContext(IO) {
        chimeApi.auth.loginViaFacebook(token).map { it.toCredentials() }
    }

    override suspend fun loginViaGoogle(token: String): Result<Credentials> = withContext(IO) {
        chimeApi.auth.loginViaGoogle(token).map { it.toCredentials() }
    }

    override suspend fun logout(): Boolean = withContext(IO) {
        chimeApi.auth.logout()
    }

    override suspend fun register(register: Register): Result<Unit> = withContext(IO) {
        chimeApi.auth.register(register.toRegistrationRequest())
    }

    override fun updateCredentials(credentials: Credentials) {
        chimeApi.auth.saveTokens(credentials.accessToken, credentials.refreshToken)
        if (credentials.currentUser.isNotBlank()) {
            AppDatabase.updateName(credentials.currentUser)
        }
    }
}
