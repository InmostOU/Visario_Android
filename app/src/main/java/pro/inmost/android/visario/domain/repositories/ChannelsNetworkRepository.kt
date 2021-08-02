package pro.inmost.android.visario.domain.repositories

import pro.inmost.android.visario.domain.entities.Channel
import pro.inmost.android.visario.domain.entities.Message

interface ChannelsNetworkRepository {
    suspend fun getChannels(): Result<List<Channel>>
    suspend fun getChannel(channelUrl: String): Result<Channel>
    suspend fun getMessages(channelUrl: String): Result<List<Message>>
    suspend fun sendMessage(channelUrl: String, text: String): Result<String>
}