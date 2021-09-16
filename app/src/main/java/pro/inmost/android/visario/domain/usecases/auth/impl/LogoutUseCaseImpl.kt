package pro.inmost.android.visario.domain.usecases.auth.impl

import pro.inmost.android.visario.domain.repositories.AccountRepository
import pro.inmost.android.visario.domain.usecases.auth.LogoutUseCase

class LogoutUseCaseImpl(private val repository: AccountRepository) : LogoutUseCase {

    override suspend fun logout() {
        repository.logout()
    }
}