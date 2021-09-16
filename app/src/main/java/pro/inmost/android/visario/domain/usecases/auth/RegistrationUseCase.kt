package pro.inmost.android.visario.domain.usecases.auth

import pro.inmost.android.visario.domain.entities.user.Register

interface RegistrationUseCase {
    /**
     * Register user in the AWS Chime
     *
     * @param register [Register] with user's credentials
     * @return [Result]
     *
     */
    suspend fun register(register: Register): Result<Unit>
}