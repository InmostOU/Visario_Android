package pro.inmost.android.visario.domain.usecases.auth.impl

import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.domain.repositories.AccountRepository
import pro.inmost.android.visario.domain.usecases.auth.UpdateCredentialsUseCase

class UpdateCredentialsUseCaseImpl(private val repository: AccountRepository) :
    UpdateCredentialsUseCase {
    override fun update(credentials: Credentials) {
        repository.updateCredentials(credentials)
    }
}