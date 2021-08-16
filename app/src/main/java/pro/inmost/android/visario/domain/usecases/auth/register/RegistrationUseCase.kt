package pro.inmost.android.visario.domain.usecases.auth.register

import pro.inmost.android.visario.domain.entities.user.Register

interface RegistrationUseCase {
    suspend fun register(register: Register): Result<Unit>
}