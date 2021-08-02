package pro.inmost.android.visario.domain.usecases.auth.login

import pro.inmost.android.visario.data.network.chimeapi.auth.model.Tokens
import pro.inmost.android.visario.domain.repositories.AccountRepository

class LoginUseCaseImpl(private val repository: AccountRepository) : LoginUseCase {

    override suspend fun login(email: String, password: String): Result<Tokens> {
        return repository.login(email, password)
    }
}