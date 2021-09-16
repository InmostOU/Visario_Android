package pro.inmost.android.visario.domain.usecases.contacts

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.contact.Contact


/**
 * Fetch user's contacts use case
 *
 * @constructor Create empty Fetch contacts use case
 */
interface FetchContactsUseCase {
    /**
     * Fetch [Contact] by username
     *
     * @param username user's unique username
     * @return [Result] that encapsulates [Contact]
     * or a failure with an arbitrary [Throwable] exception.
     */
    suspend fun fetch(username: String): Result<Contact>

    /**
     * Fetch current user's personal list of contacts
     *
     * @return list of [Contact]
     */
    suspend fun fetchAll(): List<Contact>

    /**
     * Observe current user's personal list of contacts
     *
     * @return [Flow] with list of contacts
     */
    fun observe(): Flow<List<Contact>>
}