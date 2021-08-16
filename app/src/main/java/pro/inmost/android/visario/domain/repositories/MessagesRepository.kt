package pro.inmost.android.visario.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.message.Message

interface MessagesRepository {
    fun getMessages(channelUrl: String): Flow<List<Message>>
    suspend fun sendMessage(channelUrl: String, text: String): Result<Unit>
    suspend fun refreshData(channelArn: String)
}