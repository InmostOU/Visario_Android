package pro.inmost.android.visario.domain.usecases.auth.impl

import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.domain.repositories.AccountRepository
import pro.inmost.android.visario.domain.usecases.auth.LoginUseCase

class LoginUseCaseImpl(private val repository: AccountRepository) : LoginUseCase {

    override suspend fun login(email: String, password: String): Result<Credentials> {
        return repository.login(email, password)
    }

    override suspend fun loginViaFacebook(token: String): Result<Credentials> {
        return repository.loginViaFacebook(token)
    }

    override suspend fun loginViaGoogle(token: String): Result<Credentials> {
        return repository.loginViaGoogle(token)
    }

    override suspend fun forgotPassword(email: String): Result<Unit> {
        return repository.forgotPassword(email)
    }
}