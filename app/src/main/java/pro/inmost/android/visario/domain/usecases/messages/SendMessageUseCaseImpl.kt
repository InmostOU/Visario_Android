package pro.inmost.android.visario.domain.usecases.messages

import pro.inmost.android.visario.domain.entities.message.Message
import pro.inmost.android.visario.domain.repositories.MessagesRepository

class SendMessageUseCaseImpl(private val repository: MessagesRepository) : SendMessageUseCase {
    override suspend fun send(message: Message): Result<Unit> {
        return repository.sendMessage(message)
    }
}