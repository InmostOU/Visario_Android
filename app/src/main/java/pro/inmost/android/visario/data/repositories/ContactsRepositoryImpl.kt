package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.dto.requests.contacts.EditContactRequest
import pro.inmost.android.visario.data.database.dao.ContactsDao
import pro.inmost.android.visario.data.entities.contact.toDataContact
import pro.inmost.android.visario.data.entities.contact.toDomainContact
import pro.inmost.android.visario.data.entities.contact.toDomainContacts
import pro.inmost.android.visario.data.utils.extensions.launchIO
import pro.inmost.android.visario.domain.entities.contact.Contact
import pro.inmost.android.visario.domain.repositories.ContactsRepository

class ContactsRepositoryImpl(
    private val api: ChimeApi,
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
        api.contacts.search(username).getOrDefault(listOf()).toDomainContacts()
    }

    override suspend fun searchMyContacts(query: String) = withContext(IO) {
        dao.getAll().toDomainContacts().filter { data ->
            data.username.contains(query, true) || data.fullName.contains(query, true)
        }
    }

    override suspend fun refreshData(): Unit = withContext(IO) {
        api.contacts.getContacts().onSuccess {
            dao.fullUpdate(it)
        }
    }

    override suspend fun addContact(username: String) = withContext(IO){
        api.contacts.addContact(username).onSuccess {
            refreshData()
        }
    }

    override suspend fun deleteContact(id: Long) = withContext(IO) {
        api.contacts.deleteContact(id).onSuccess {
            refreshData()
        }
    }

    override suspend fun updateContact(contact: Contact) = withContext(IO)  {
        val contactData = contact.toDataContact()
        dao.update(contactData)
        val request = EditContactRequest(
            id = contactData.id,
            firstName = contactData.firstName,
            lastName = contactData.lastName
        )
        api.contacts.editContact(request)
    }

}