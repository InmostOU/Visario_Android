package pro.inmost.android.visario.domain.usecases.messages.impl

import pro.inmost.android.visario.domain.repositories.MessagesRepository
import pro.inmost.android.visario.domain.usecases.messages.UpdateMessagesReadStatusUseCase

class UpdateMessagesReadStatusUseCaseImpl(private val repository: MessagesRepository) :
    UpdateMessagesReadStatusUseCase {
    override suspend fun updateAll(channelArn: String, read: Boolean) {
        repository.updateReadStatusForAll(channelArn, read)
    }

    override suspend fun update(messageId: String, read: Boolean) {
        repository.updateReadStatus(messageId, read)
    }
}