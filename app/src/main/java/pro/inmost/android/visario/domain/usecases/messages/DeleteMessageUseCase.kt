package pro.inmost.android.visario.domain.usecases.messages

import pro.inmost.android.visario.domain.entities.message.ReceivingMessage


/**
 * Delete message use case
 *
 */
interface DeleteMessageUseCase  {
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
}