package pro.inmost.android.visario.domain.usecases.contacts

import pro.inmost.android.visario.domain.entities.Contact

interface SearchContactsUseCase {
    suspend fun searchGlobal(username: String): List<Contact>
    suspend fun searchLocal(query: String): List<Contact>
}