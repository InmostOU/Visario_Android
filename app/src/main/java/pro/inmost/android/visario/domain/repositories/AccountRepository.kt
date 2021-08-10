package pro.inmost.android.visario.domain.repositories

import pro.inmost.android.visario.domain.entities.Credentials
import pro.inmost.android.visario.domain.entities.User

interface AccountRepository {
    suspend fun login(email: String, password: String): Result<Credentials>
    suspend fun logout(): Boolean
    suspend fun register(user: User): Result<Unit>
    fun updateCredentials(credentials: Credentials)
}