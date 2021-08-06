package pro.inmost.android.visario.domain.usecases.contacts

import kotlinx.coroutines.flow.flow
import pro.inmost.android.visario.domain.entities.Contact
import pro.inmost.android.visario.domain.repositories.ContactsLocalRepository

class FetchContactsUseCaseImpl(private val localRepository: ContactsLocalRepository) : FetchContactsUseCase {

    override fun fetch(id: Int) = flow {
        localRepository.getContact(id)?.let {
            emit(it)
        }
    }

    override fun fetchAll() = flow {
        val contacts = localRepository.getContacts()
        emit(contacts)
    }

}