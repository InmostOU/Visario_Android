package pro.inmost.android.visario.domain.usecases.messages.impl

import pro.inmost.android.visario.domain.repositories.MessagesRepository
import pro.inmost.android.visario.domain.usecases.messages.EditMessageUseCase

class EditMessageUseCaseImpl(private val repository: MessagesRepository) : EditMessageUseCase {
    override suspend fun edit(messageId: String, content: String, channelArn: String): Result<Unit> {
        return repository.edit(messageId, content, channelArn)
    }
}