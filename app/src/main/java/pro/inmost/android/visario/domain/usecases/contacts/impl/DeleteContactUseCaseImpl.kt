package pro.inmost.android.visario.domain.usecases.contacts.impl

import pro.inmost.android.visario.domain.repositories.ContactsRepository
import pro.inmost.android.visario.domain.usecases.contacts.DeleteContactUseCase

class DeleteContactUseCaseImpl(private val repository: ContactsRepository) : DeleteContactUseCase {
    override suspend fun delete(id: Long): Result<Unit> {
        return repository.deleteContact(id)
    }
}