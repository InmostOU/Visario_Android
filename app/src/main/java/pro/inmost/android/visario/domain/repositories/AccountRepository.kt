package pro.inmost.android.visario.domain.repositories

import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.domain.entities.user.Register

interface AccountRepository {
    suspend fun login(email: String, password: String): Result<Credentials>
    suspend fun logout(): Boolean
    suspend fun register(register: Register): Result<Unit>
    fun updateCredentials(credentials: Credentials)
}