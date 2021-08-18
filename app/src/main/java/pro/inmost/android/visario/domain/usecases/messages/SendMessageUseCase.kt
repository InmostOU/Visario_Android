package pro.inmost.android.visario.domain.usecases.messages

import pro.inmost.android.visario.domain.entities.message.Message

interface SendMessageUseCase {
    suspend fun send(message: Message): Result<Unit>
}