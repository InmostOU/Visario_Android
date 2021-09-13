package pro.inmost.android.visario.domain.usecases.messages

import pro.inmost.android.visario.domain.repositories.MessagesRepository

class SendMessageUseCaseImpl(private val repository: MessagesRepository) : SendMessageUseCase {
    override suspend fun send(message: String, channelArn: String): Result<Unit> {
        return repository.sendMessage(message, channelArn)
    }
}