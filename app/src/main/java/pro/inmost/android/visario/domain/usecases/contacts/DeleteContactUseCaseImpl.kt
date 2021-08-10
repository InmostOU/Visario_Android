package pro.inmost.android.visario.domain.usecases.contacts

import pro.inmost.android.visario.domain.repositories.ContactsRepository

class DeleteContactUseCaseImpl(private val repository: ContactsRepository) : DeleteContactUseCase {
    override suspend fun delete(username: String): Result<Unit> {
        return repository.deleteContact(username)
    }
}