package pro.inmost.android.visario.domain.usecases.contacts.impl

import pro.inmost.android.visario.domain.repositories.ContactsRepository
import pro.inmost.android.visario.domain.usecases.contacts.AddContactUseCase

class AddContactUseCaseImpl(private val repository: ContactsRepository) : AddContactUseCase {
    override suspend fun add(username: String): Result<Unit> {
        return repository.addContact(username)
    }
}