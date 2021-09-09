package pro.inmost.android.visario.domain.usecases.messages

interface SendMessageUseCase {
    suspend fun send(message: String, channelArn: String): Result<Unit>
}