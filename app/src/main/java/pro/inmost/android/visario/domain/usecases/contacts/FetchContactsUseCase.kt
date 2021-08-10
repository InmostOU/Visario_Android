package pro.inmost.android.visario.domain.usecases.contacts

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.Contact

interface FetchContactsUseCase {
    suspend fun fetch(username: String): Result<Contact>
    suspend fun fetchAll(): List<Contact>
    fun observe(): Flow<List<Contact>>
}