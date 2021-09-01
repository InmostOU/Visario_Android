package pro.inmost.android.visario.domain.usecases.messages

import pro.inmost.android.visario.domain.repositories.MessagesRepository

class UpdateMessagesReadStatusUseCaseImpl(private val repository: MessagesRepository) : UpdateMessagesReadStatusUseCase {
    override suspend fun updateAll(channelArn: String, read: Boolean) {
        repository.updateReadStatusForAll(channelArn, read)
    }

    override suspend fun update(messageId: String, read: Boolean) {
        repository.updateReadStatus(messageId, read)
    }
}