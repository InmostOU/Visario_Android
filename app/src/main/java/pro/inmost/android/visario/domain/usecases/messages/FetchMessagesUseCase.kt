package pro.inmost.android.visario.domain.usecases.messages

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.message.Message


/**
 * Fetch messages use case
 *
 */
interface FetchMessagesUseCase {
    /**
     * Get observable list of messages from specified channel
     *
     * @param channelArn channel url from AWS Chime
     * @return [Flow] with list of [Message]
     */
    fun fetch(channelArn: String): Flow<List<Message>>
}