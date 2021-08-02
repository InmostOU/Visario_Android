package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.network.chimeapi.messages.MessageRequest
import pro.inmost.android.visario.data.network.utils.toDomainChannel
import pro.inmost.android.visario.data.network.utils.toDomainMessages
import pro.inmost.android.visario.domain.repositories.ChannelsNetworkRepository
import pro.inmost.android.visario.domain.entities.Channel
import pro.inmost.android.visario.domain.entities.Message
import pro.inmost.android.visario.ui.utils.log

class ChannelsNetworkRepositoryImpl(private val api: ChimeApi): ChannelsNetworkRepository{

    override suspend fun getChannels(): Result<List<Channel>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.channels.getChannels()
                if (response.status == STATUS_OK) {
                    val channels = response.channels.mapNotNull { data ->
                        val messages = getMessages(data.channelArn).getOrNull() ?: listOf()
                        data.toDomainChannel(messages)
                    }
                    Result.success(channels)
                } else {
                    Result.failure(Throwable(response.message))
                }
            } catch (e: Throwable) {
                Result.failure(e)
            }
        }
    }

    private suspend fun loadMessages(channelArn: String) = api.messages.getMessages(channelArn)

    override suspend fun getChannel(channelUrl: String): Result<Channel> {
        getChannels().onSuccess { channels ->
            val channel = channels.find { it.url == channelUrl }
            return if (channel != null) Result.success(channel)
            else Result.failure(Throwable("Not find"))
        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(Throwable("Unknown error"))
    }

    override suspend fun getMessages(channelUrl: String): Result<List<Message>> {
        val response = loadMessages(channelUrl)
        return if (response.status == STATUS_OK){
            Result.success(response.messages.toDomainMessages())
        } else {
            Result.failure(Throwable(response.message))
        }
    }

    override suspend fun sendMessage(channelUrl: String, text: String): Result<String> {
        return withContext(Dispatchers.IO){
            try {
                val request = MessageRequest(
                    channelArn = channelUrl,
                    content = text
                )
                val response = api.messages.sendMessage(request)
                if (!response.content.isNullOrBlank()) {
                    Result.success(response.content)
                } else {
                    Result.failure(Throwable("Send failed"))
                }
            } catch (t: Throwable) {
                log("sendMessage error: ${t.message}")
                Result.failure(Throwable(t.message))
            }
        }
    }
}
