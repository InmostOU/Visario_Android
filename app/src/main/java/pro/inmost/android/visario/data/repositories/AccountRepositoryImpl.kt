package pro.inmost.android.visario.data.repositories

import pro.inmost.android.visario.data.database.AppDatabase
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.network.chimeapi.auth.Authenticator
import pro.inmost.android.visario.domain.entities.Credentials
import pro.inmost.android.visario.data.network.utils.toRegistrationRequest
import pro.inmost.android.visario.domain.repositories.AccountRepository
import pro.inmost.android.visario.domain.entities.User

class AccountRepositoryImpl(private val chimeApi: ChimeApi) : AccountRepository {

    override suspend fun login(email: String, password: String): Result<Credentials> {
        return try {
            val response = chimeApi.auth.login(email, password)
            if (response.status == STATUS_OK){
                val credentials = Credentials(
                    currentUser = email,
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken)

                Result.success(credentials)
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

    override fun updateCredentials(credentials: Credentials) {
        chimeApi.auth.saveTokens(credentials.accessToken, credentials.refreshToken)
        if (credentials.currentUser.isNotBlank()){
            AppDatabase.updateName(credentials.currentUser)
        }
    }
}