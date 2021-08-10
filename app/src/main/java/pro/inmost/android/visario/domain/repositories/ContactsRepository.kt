package pro.inmost.android.visario.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.Contact

interface ContactsRepository {
    fun observeContacts(): Flow<List<Contact>>
    suspend fun getContacts(): List<Contact>
    suspend fun getContact(username: String): Result<Contact>
    suspend fun searchContacts(username: String): List<Contact>
    suspend fun searchMyContacts(query: String): List<Contact>
    suspend fun refreshData()
    suspend fun addContact(username: String): Result<Unit>
    suspend fun deleteContact(username: String): Result<Unit>
    suspend fun updateContact(contact: Contact)
}