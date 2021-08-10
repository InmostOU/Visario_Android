package pro.inmost.android.visario.domain.usecases.messages

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.Message

interface FetchMessagesUseCase {
    fun fetch(channel: String): Flow<List<Message>>
}