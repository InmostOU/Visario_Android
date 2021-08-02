package pro.inmost.android.visario.domain.usecases.auth.register

import pro.inmost.android.visario.domain.entities.User

interface RegistrationUseCase {
    suspend fun register(user: User): Result<String>
}