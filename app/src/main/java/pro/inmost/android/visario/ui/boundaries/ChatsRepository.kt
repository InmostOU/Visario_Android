package pro.inmost.android.visario.ui.boundaries

import kotlinx.coroutines.flow.Flow

interface ChatsRepository<T> {
    suspend fun getChats(): List<T>
    fun observeChats(timeoutInMillis: Long): Flow<List<T>>
}