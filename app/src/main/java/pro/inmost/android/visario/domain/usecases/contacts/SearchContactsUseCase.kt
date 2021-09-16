package pro.inmost.android.visario.domain.usecases.contacts

import pro.inmost.android.visario.domain.entities.contact.Contact


/**
 * Search contacts use case
 *
 */
interface SearchContactsUseCase {
    /**
     * Global search of users by username
     *
     * @param username user's unique username
     * @return list of [Contact] whose username contain the string in param
     */
    suspend fun searchGlobal(username: String): List<Contact>

    /**
     * Search contacts by username that added in user's personal contact list
     *
     * @param query string to be found in username, first or last name
     * @return list of [Contact] whose username, first or last name contain the query
     */
    suspend fun searchLocal(query: String): List<Contact>
}