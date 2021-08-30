package pro.inmost.android.visario.domain.usecases.messages

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.message.Message
import pro.inmost.android.visario.domain.repositories.MessagesRepository

class FetchMessagesUseCaseImpl(private val repository: MessagesRepository) : FetchMessagesUseCase {
    override fun fetch(channel: String): Flow<List<Message>> {
        return repository.getMessages(channel)
    }
}