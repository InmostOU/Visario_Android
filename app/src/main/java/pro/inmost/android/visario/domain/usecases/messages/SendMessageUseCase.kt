package pro.inmost.android.visario.domain.usecases.messages

interface SendMessageUseCase {
    suspend fun send(channel: String, message: String): Result<String>
}