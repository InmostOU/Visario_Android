package pro.inmost.android.visario.domain.usecases.auth.register

import pro.inmost.android.visario.domain.repositories.AccountRepository
import pro.inmost.android.visario.domain.entities.User

class RegistrationUseCaseImpl(private val repository: AccountRepository) : RegistrationUseCase {

    override suspend fun register(user: User): Result<Unit> {
        return repository.register(user)
    }
}