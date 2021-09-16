package pro.inmost.android.visario.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.contact.Contact

interface ContactsRepository {
    /**
     * Get observable list of user's [Contact]
     *
     * @return [Flow] with list of contacts
     */
    fun observeContacts(): Flow<List<Contact>>

    /**
     * Get list of user's [Contact]
     *
     * @return list of [Contact]
     */
    suspend fun getContacts(): List<Contact>

    /**
     * Get [Contact] by username
     *
     * @param username
     * @return [Result] that encapsulates [Contact] or a failure with an arbitrary [Throwable] exception.
     */
    suspend fun getContact(username: String): Result<Contact>

    /**
     * Search contacts by username
     *
     * @param username unique username
     * @return list of [Contact] whose username contain the word in param
     */
    suspend fun searchContacts(username: String): List<Contact>

    /**
     * Search contacts by username that added in user's personal contact list
     *
     * @param query string to be found in username, first or last name
     * @return list of [Contact] whose username, first or last name contain the query
     */
    suspend fun searchMyContacts(query: String): List<Contact>

    /**
     * Refresh contacts data
     *
     */
    suspend fun refreshData()

    /**
     * Add contact to personal contact list
     *
     * @param username
     * @return
     */
    suspend fun addContact(username: String): Result<Unit>

    /**
     * Delete contact
     *
     * @param id
     * @return [Result]
     */
    suspend fun deleteContact(id: Long): Result<Unit>

    /**
     * Update contact info
     *
     * @param contact
     * @return [Result]
     */
    suspend fun updateContact(contact: Contact): Result<Unit>
}