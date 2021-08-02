package pro.inmost.android.visario.domain.usecases.auth.login

import pro.inmost.android.visario.data.network.chimeapi.auth.model.Tokens

interface LoginUseCase {
    suspend fun login(email: String, password: String): Result<Tokens>
}