package pro.inmost.android.visario.domain.usecases.auth.register

import pro.inmost.android.visario.domain.repositories.AccountRepository
import pro.inmost.android.visario.domain.entities.user.Register

class RegistrationUseCaseImpl(private val repository: AccountRepository) : RegistrationUseCase {

    override suspend fun register(register: Register): Result<Unit> {
        return repository.register(register)
    }
}