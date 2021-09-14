package pro.inmost.android.visario.domain.usecases.messages

interface EditMessageUseCase {
    suspend fun edit(messageId: String, content: String, channelArn: String): Result<Unit>
}