package pro.inmost.android.visario.domain.usecases.contacts.impl

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.contact.Contact
import pro.inmost.android.visario.domain.repositories.ContactsRepository
import pro.inmost.android.visario.domain.usecases.contacts.FetchContactsUseCase

class FetchContactsUseCaseImpl(private val repository: ContactsRepository) : FetchContactsUseCase {

    override suspend fun fetch(username: String): Result<Contact>{
        return repository.getContact(username)
    }

    override suspend fun fetchAll(): List<Contact> {
        return repository.getContacts()
    }

    override fun observe(): Flow<List<Contact>> {
        return repository.observeContacts()
    }
}