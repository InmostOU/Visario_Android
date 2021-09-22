package pro.inmost.android.visario.domain.usecases.messages

import pro.inmost.android.visario.domain.entities.message.ReceivingMessage


/**
 * Update messages read status use case
 *
 */
interface UpdateMessagesReadStatusUseCase {
    /**
     * Update read status for all messages in channel
     *
     * @param channelArn channel url from AWS Chime
     * @param read true if messages read, else false
     */
    suspend fun updateAll(channelArn: String, read: Boolean)

    /**
     * Update read status for specified message
     *
     * @param messageId id of [ReceivingMessage]
     * @param read true if message read, else false
     */
    suspend fun update(messageId: String, read: Boolean)
}