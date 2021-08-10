package pro.inmost.android.visario.domain.usecases.contacts

import pro.inmost.android.visario.domain.entities.Contact

interface UpdateContactUseCase {
    suspend fun update(contact: Contact)
}