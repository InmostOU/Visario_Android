package pro.inmost.android.visario.domain.usecases.messages.impl

import pro.inmost.android.visario.domain.entities.message.SendingMessage
import pro.inmost.android.visario.domain.repositories.MessagesRepository
import pro.inmost.android.visario.domain.usecases.messages.SendMessageUseCase

class SendMessageUseCaseImpl(private val repository: MessagesRepository) : SendMessageUseCase {
    override suspend fun send(message: SendingMessage): Result<Unit> {
        return repository.sendMessage(message)
    }

    override suspend fun resend(messageId: String): Result<Unit> {
        return repository.resendMessage(messageId)
    }
}