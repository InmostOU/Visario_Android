package pro.inmost.android.visario.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.message.ReceivingMessage
import pro.inmost.android.visario.domain.entities.message.SendingMessage

interface MessagesRepository {
    /**
     * Get observable list of messages from specified channel
     *
     * @param channelArn channel url from AWS Chime
     * @return [Flow] with list of [ReceivingMessage]
     */
    fun getMessages(channelArn: String): Flow<List<ReceivingMessage>>

    /**
     * Send message to the channel
     *
     * @param message [SendingMessage]
     * @return [Result]
     */
    suspend fun sendMessage(message: SendingMessage): Result<Unit>

    /**
     * Resend failed message
     *
     * @param messageId id of [ReceivingMessage]
     * @return [Result]
     */
    suspend fun resendMessage(messageId: String): Result<Unit>

    /**
     * Edit a sent message
     *
     * @param messageId id of [ReceivingMessage]
     * @param content message text
     * @param channelArn channel url from AWS Chime
     * @return [Result]
     */
    suspend fun edit(messageId: String, content: String, channelArn: String): Result<Unit>

    /**
     * Delete message from channel
     *
     * @param messageId id of [ReceivingMessage]
     * @return [Result]
     */
    suspend fun delete(messageId: String): Result<Unit>

    /**
     * Delete message from local database
     *
     * @param messageId id of [ReceivingMessage]
     */
    suspend fun deleteLocal(messageId: String)

    /**
     * Refresh all messages data
     *
     * @param channelArn channel url from AWS Chime
     */
    suspend fun refreshData(channelArn: String)

    /**
     * Mark all message as read
     *
     * @param channelArn channel url from AWS Chime
     */
    suspend fun markAllMessageAsRead(channelArn: String)

    /**
     * Update read status for all messages in channel
     *
     * @param channelArn channel url from AWS Chime
     * @param read true if messages read, else false
     */
    suspend fun updateReadStatusForAll(channelArn: String, read: Boolean)

    /**
     * Update read status for specified message
     *
     * @param messageId id of [ReceivingMessage]
     * @param read true if message read, else false
     */
    suspend fun updateReadStatus(messageId: String, read: Boolean)
}