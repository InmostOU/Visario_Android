package pro.inmost.android.visario.domain.usecases.messages.impl

import pro.inmost.android.visario.domain.repositories.MessagesRepository
import pro.inmost.android.visario.domain.usecases.messages.DeleteMessageUseCase

class DeleteMessageUseCaseImpl(private val repository: MessagesRepository) : DeleteMessageUseCase {
    override suspend fun delete(messageId: String): Result<Unit> {
        return repository.delete(messageId)
    }

    override suspend fun deleteLocal(messageId: String) {
        repository.deleteLocal(messageId)
    }
}