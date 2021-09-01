package pro.inmost.android.visario.domain.usecases.contacts

import pro.inmost.android.visario.domain.entities.contact.Contact

interface UpdateContactUseCase {
    suspend fun update(contact: Contact)
}