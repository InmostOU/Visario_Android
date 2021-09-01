package pro.inmost.android.visario.domain.usecases.auth.logout

import pro.inmost.android.visario.domain.repositories.AccountRepository

class LogoutUseCaseImpl(private val repository: AccountRepository) : LogoutUseCase {

    override suspend fun logout() {
        repository.logout()
    }
}