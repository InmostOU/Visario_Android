package pro.inmost.android.visario.domain.usecases.messages

interface UpdateMessagesReadStatusUseCase {
    suspend fun updateAll(channelArn: String, read: Boolean)
    suspend fun update(messageId: String, read: Boolean)
}