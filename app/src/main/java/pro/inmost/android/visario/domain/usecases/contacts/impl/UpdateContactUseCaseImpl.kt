package pro.inmost.android.visario.domain.usecases.contacts.impl

import pro.inmost.android.visario.domain.entities.contact.Contact
import pro.inmost.android.visario.domain.repositories.ContactsRepository
import pro.inmost.android.visario.domain.usecases.contacts.UpdateContactUseCase

class UpdateContactUseCaseImpl(private val repository: ContactsRepository) : UpdateContactUseCase {
    override suspend fun update(contact: Contact)  {
        repository.updateContact(contact)
    }
}