package pro.inmost.android.visario.ui.boundaries

import pro.inmost.android.visario.core.data.chimeapi.messages.Message
import pro.inmost.android.visario.core.data.chimeapi.messages.MessageRequest

interface MessagesRepository {
    suspend fun getMessages(channelArn: String): Result<List<Message>>
    suspend fun sendMessage(request: MessageRequest): Result<String>
}