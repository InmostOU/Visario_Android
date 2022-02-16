package pro.inmost.android.visario.domain.usecases.auth.impl

import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.domain.repositories.AccountRepository
import pro.inmost.android.visario.domain.usecases.auth.LoginUseCase

class LoginUseCaseImpl(private val repository: AccountRepository) : LoginUseCase {

    override suspend fun login(email: String, password: String): Result<Credentials> {
        return repository.login(email, password)
    }

    override suspend fun loginViaFacebook(): Result<Int> {
        return repository.loginViaFacebook()
    }
}