package pro.inmost.android.visario.domain.boundaries

import pro.inmost.android.visario.data.network.chimeapi.messages.Message

interface MessagesLocalRepository {
    suspend fun getMessages(channelArn: String): List<Message>
    suspend fun saveMessages(channels: List<Message>)
}