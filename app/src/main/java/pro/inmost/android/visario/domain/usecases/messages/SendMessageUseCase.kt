package pro.inmost.android.visario.domain.usecases.messages

import pro.inmost.android.visario.domain.entities.message.ReceivingMessage
import pro.inmost.android.visario.domain.entities.message.SendingMessage


/**
 * Send message use case
 *
 */
interface SendMessageUseCase {
    /**
     * Send message to the channel
     *
     * @param message text of message
     * @param message [SendingMessage]
     * @return [Result]
     */
    suspend fun send(message: SendingMessage): Result<Unit>

    /**
     * Resend failed message
     *
     * @param messageId id of [ReceivingMessage]
     * @return [Result]
     */
    suspend fun resend(messageId: String): Result<Unit>
}