package pro.inmost.android.visario.domain.usecases.messages

import pro.inmost.android.visario.domain.entities.message.Message


/**
 * Send message use case
 *
 */
interface SendMessageUseCase {
    /**
     * Send message to the channel
     *
     * @param message text of message
     * @param channelArn channel url from AWS Chime
     * @return [Result]
     */
    suspend fun send(message: String, channelArn: String): Result<Unit>

    /**
     * Resend failed message
     *
     * @param messageId id of [Message]
     * @return [Result]
     */
    suspend fun resend(messageId: String): Result<Unit>
}