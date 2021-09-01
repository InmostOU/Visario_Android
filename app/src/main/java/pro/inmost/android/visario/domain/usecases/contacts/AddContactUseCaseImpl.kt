package pro.inmost.android.visario.domain.usecases.contacts

import pro.inmost.android.visario.domain.repositories.ContactsRepository

class AddContactUseCaseImpl(private val repository: ContactsRepository) : AddContactUseCase {
    override suspend fun add(username: String): Result<Unit> {
        return repository.addContact(username)
    }
}