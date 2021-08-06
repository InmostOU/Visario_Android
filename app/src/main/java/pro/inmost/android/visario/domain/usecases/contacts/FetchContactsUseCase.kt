package pro.inmost.android.visario.domain.usecases.contacts

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.Contact

interface FetchContactsUseCase {
    fun fetch(id: Int): Flow<Contact>
    fun fetchAll(): Flow<List<Contact>>
}