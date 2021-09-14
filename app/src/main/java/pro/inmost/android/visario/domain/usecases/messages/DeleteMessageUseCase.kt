package pro.inmost.android.visario.domain.usecases.messages

interface DeleteMessageUseCase  {
    suspend fun delete(messageId: String): Result<Unit>
    suspend fun deleteLocal(messageId: String)
}