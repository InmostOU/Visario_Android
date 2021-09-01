package pro.inmost.android.visario.domain.usecases.contacts

import pro.inmost.android.visario.domain.entities.contact.Contact
import pro.inmost.android.visario.domain.repositories.ContactsRepository

class SearchContactsUseCaseImpl(private val repository: ContactsRepository) : SearchContactsUseCase {
    override suspend fun searchGlobal(username: String): List<Contact> {
        return repository.searchContacts(username)
    }

    override suspend fun searchLocal(query: String): List<Contact> {
        return repository.searchMyContacts(query)
    }
}