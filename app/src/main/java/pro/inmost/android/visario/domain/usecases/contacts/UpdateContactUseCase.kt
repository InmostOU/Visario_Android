package pro.inmost.android.visario.domain.usecases.contacts

import pro.inmost.android.visario.domain.entities.contact.Contact


/**
 * Update contact use case
 *
 */
interface UpdateContactUseCase {
    /**
     * Update contact's info (first and last name)
     *
     * @param contact [Contact]
     */
    suspend fun update(contact: Contact)
}