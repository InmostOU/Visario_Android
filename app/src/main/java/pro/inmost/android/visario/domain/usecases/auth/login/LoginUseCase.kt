package pro.inmost.android.visario.domain.usecases.auth.login

import pro.inmost.android.visario.domain.entities.Credentials

interface LoginUseCase {
    suspend fun login(email: String, password: String): Result<Credentials>
}