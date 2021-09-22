package pro.inmost.android.visario.domain.usecases.messages

import pro.inmost.android.visario.domain.entities.message.ReceivingMessage


/**
 * Edit message use case
 *
 */
interface EditMessageUseCase {
    /**
     * Edit a sent message
     *
     * @param messageId id of [ReceivingMessage]
     * @param content message text
     * @param channelArn channel url from AWS Chime
     * @return [Result]
     */
    suspend fun edit(messageId: String, content: String, channelArn: String): Result<Unit>
}