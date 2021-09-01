package pro.inmost.android.visario.domain.usecases.contacts

import pro.inmost.android.visario.domain.repositories.ContactsRepository

class DeleteContactUseCaseImpl(private val repository: ContactsRepository) : DeleteContactUseCase {
    override suspend fun delete(id: Int): Result<Unit> {
        return repository.deleteContact(id)
    }
}