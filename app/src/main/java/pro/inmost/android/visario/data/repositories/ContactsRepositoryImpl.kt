package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.database.dao.ContactsDao
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.utils.launchIO
import pro.inmost.android.visario.data.utils.toDataContact
import pro.inmost.android.visario.data.utils.toDomainContact
import pro.inmost.android.visario.data.utils.toDomainContacts
import pro.inmost.android.visario.domain.entities.Contact
import pro.inmost.android.visario.domain.repositories.ContactsRepository

class ContactsRepositoryImpl(
    private val chimeApi: ChimeApi,
    private val dao: ContactsDao
) : ContactsRepository {

    override fun observeContacts(): Flow<List<Contact>> {
        launchIO { refreshData() }
        return dao.getAllObservable().map { it.toDomainContacts() }
    }

    override suspend fun getContacts() = withContext(IO){
        dao.getAll().toDomainContacts()
    }

    override suspend fun getContact(username: String) = withContext(IO) {
        val result = dao.getAll().find { it.username == username }
        if (result != null){
            Result.success(result.toDomainContact())
        } else {
            Result.failure(Throwable("Not found"))
        }
    }

    override suspend fun searchContacts(username: String) = withContext(IO) {
        chimeApi.contacts.search(username).getOrDefault(listOf()).toDomainContacts()
    }

    override suspend fun searchMyContacts(query: String) = withContext(IO) {
        dao.getAll().toDomainContacts().filter { data ->
            data.username.contains(query, true) || data.fullName.contains(query, true)
        }
    }

    override suspend fun refreshData(): Unit = withContext(IO) {
        chimeApi.contacts.getContacts().onSuccess {
            dao.fullUpdate(it)
        }
    }

    override suspend fun addContact(username: String) = withContext(IO){
        chimeApi.contacts.addContact(username).onSuccess {
            refreshData()
        }
    }

    override suspend fun deleteContact(username: String) = withContext(IO) {
        chimeApi.contacts.deleteContact(username).onSuccess {
            refreshData()
        }
    }

    override suspend fun updateContact(contact: Contact) = withContext(IO)  {
        dao.update(contact.toDataContact())
    }

}