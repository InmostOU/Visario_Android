package pro.inmost.android.visario.domain.boundaries

import pro.inmost.android.visario.data.network.chimeapi.messages.Message
import pro.inmost.android.visario.data.network.chimeapi.messages.MessageRequest

interface MessagesNetworkRepository {
    suspend fun getMessages(channelArn: String): Result<List<Message>>
    suspend fun sendMessage(request: MessageRequest): Result<String>
}