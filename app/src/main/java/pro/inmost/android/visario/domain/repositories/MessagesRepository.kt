package pro.inmost.android.visario.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.message.Message

interface MessagesRepository {
    fun getMessages(channelUrl: String): Flow<List<Message>>
    suspend fun sendMessage(message: String, channelArn: String): Result<Unit>
    suspend fun resendMessage(messageId: String): Result<Unit>
    suspend fun edit(messageId: String, content: String, channelArn: String): Result<Unit>
    suspend fun delete(messageId: String): Result<Unit>
    suspend fun deleteLocal(messageId: String)
    suspend fun refreshData(channelArn: String)
    suspend fun markAllMessageAsRead(channelArn: String)
    suspend fun updateReadStatusForAll(channelArn: String, read: Boolean)
    suspend fun updateReadStatus(messageId: String, read: Boolean)
}