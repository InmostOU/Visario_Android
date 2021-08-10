package pro.inmost.android.visario.domain.usecases.contacts

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.data.utils.launchIO
import pro.inmost.android.visario.domain.entities.Contact
import pro.inmost.android.visario.domain.repositories.ContactsRepository

class FetchContactsUseCaseImpl(private val repository: ContactsRepository) : FetchContactsUseCase {

    override suspend fun fetch(username: String): Result<Contact>{
        return repository.getContact(username)
    }

    override suspend fun fetchAll(): List<Contact> {
        return repository.getContacts()
    }

    override fun observe(): Flow<List<Contact>> {
        launchIO { repository.refreshData() }
        return repository.observeContacts()
    }
}