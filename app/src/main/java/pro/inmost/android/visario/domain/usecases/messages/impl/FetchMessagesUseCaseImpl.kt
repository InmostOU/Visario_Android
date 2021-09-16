package pro.inmost.android.visario.domain.usecases.messages.impl

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.message.Message
import pro.inmost.android.visario.domain.repositories.MessagesRepository
import pro.inmost.android.visario.domain.usecases.messages.FetchMessagesUseCase

class FetchMessagesUseCaseImpl(private val repository: MessagesRepository) : FetchMessagesUseCase {
    override fun fetch(channelArn: String): Flow<List<Message>> {
        return repository.getMessages(channelArn)
    }
}