package pro.inmost.android.visario.domain.usecases.auth.credentials

import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.domain.repositories.AccountRepository

class UpdateCredentialsUseCaseImpl(private val repository: AccountRepository) :
    UpdateCredentialsUseCase {
    override fun update(credentials: Credentials) {
        repository.updateCredentials(credentials)
    }
}