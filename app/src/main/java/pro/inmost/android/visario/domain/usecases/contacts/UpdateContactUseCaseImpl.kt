package pro.inmost.android.visario.domain.usecases.contacts

import pro.inmost.android.visario.domain.entities.Contact
import pro.inmost.android.visario.domain.repositories.ContactsRepository

class UpdateContactUseCaseImpl(private val repository: ContactsRepository) : UpdateContactUseCase {
    override suspend fun update(contact: Contact)  {
        repository.updateContact(contact)
    }
}