package pro.inmost.android.visario.domain.boundaries

import pro.inmost.android.visario.data.network.chimeapi.auth.model.RegisterRequest
import pro.inmost.android.visario.data.network.chimeapi.auth.model.Tokens

interface AccountRepository {
    suspend fun login(email: String, password: String): Result<Tokens>
    suspend fun logout(): Boolean
    suspend fun register(body: RegisterRequest): Result<String>
}