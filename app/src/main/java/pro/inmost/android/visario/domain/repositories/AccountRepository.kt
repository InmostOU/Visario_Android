package pro.inmost.android.visario.domain.repositories

import pro.inmost.android.visario.data.network.chimeapi.auth.model.Tokens
import pro.inmost.android.visario.domain.entities.User

interface AccountRepository {
    suspend fun login(email: String, password: String): Result<Tokens>
    suspend fun logout(): Boolean
    suspend fun register(user: User): Result<String>
    fun updateTokens(tokens: Tokens)
}