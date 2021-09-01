package pro.inmost.android.visario.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.message.Message

interface MessagesRepository {
    fun getMessages(channelUrl: String): Flow<List<Message>>
    suspend fun sendMessage(message: Message): Result<Unit>
    suspend fun refreshData(channelArn: String)
    suspend fun markAllMessageAsRead(channelArn: String)
    suspend fun updateReadStatusForAll(channelArn: String, read: Boolean)
    suspend fun updateReadStatus(messageId: String, read: Boolean)
}