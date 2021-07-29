package pro.inmost.android.visario.ui.boundaries

import pro.inmost.android.visario.core.data.chimeapi.auth.model.RegisterRequestBody
import pro.inmost.android.visario.core.data.chimeapi.auth.model.Tokens

interface AccountRepository {
    suspend fun login(email: String, password: String): Result<Tokens>
    suspend fun logout(): Boolean
    suspend fun register(body: RegisterRequestBody): Result<String>
}