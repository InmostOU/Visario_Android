package pro.inmost.android.visario.data.repositories

import android.text.format.DateFormat
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.network.chimeapi.auth.Authenticator
import pro.inmost.android.visario.data.network.chimeapi.messages.MessageRequest
import pro.inmost.android.visario.data.network.chimeapi.messages.MessagesResponse
import pro.inmost.android.visario.data.network.utils.toDomainChannel
import pro.inmost.android.visario.data.network.utils.toDomainMessages
import pro.inmost.android.visario.data.network.utils.urlEncode
import pro.inmost.android.visario.domain.entities.Channel
import pro.inmost.android.visario.domain.entities.Message
import pro.inmost.android.visario.domain.repositories.ChannelsNetworkRepository
import pro.inmost.android.visario.ui.utils.log

class ChannelsNetworkRepositoryImpl(private val api: ChimeApi): ChannelsNetworkRepository{

    override suspend fun getChannels(): Result<List<Channel>> {
        return withContext(IO) {
            try {
                val response = api.channels.getChannels()
                log("response: $response")
                if (response.status == STATUS_OK) {
                    val channels = response.channels.mapNotNull { data ->
                        val messagesResponse = loadMessages(data.channelArn)
                        log("messages response: $messagesResponse")
                        val messages = if (messagesResponse.status == STATUS_OK){
                            messagesResponse.messages
                        } else listOf()
                        data.toDomainChannel(messages)
                    }
                    log("channels: $channels")
                    Result.success(channels)
                } else {
                    Result.failure(Throwable(response.message))
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }

    private suspend fun loadMessages(channelArn: String): MessagesResponse {
        return api.messages.getMessages(channelArn)
    }

    override suspend fun getChannel(channelUrl: String): Result<Channel> {
       // startSession()

        getChannels().onSuccess { channels ->
            val channel = channels.find { it.url == channelUrl.urlEncode() }
            return if (channel != null) {
                Result.success(channel)
            }
            else Result.failure(Throwable("Not find"))
        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(Throwable("Unknown error"))
    }

    private suspend fun startSession() {

    }

    override suspend fun getMessages(channelUrl: String): Result<List<Message>> {
        return withContext(IO){
            try {
                val response = loadMessages(channelUrl)
                if (response.status == STATUS_OK){
                    Result.success(response.messages.toDomainMessages())
                } else {
                    Result.failure(Throwable(response.message))
                }
            } catch (t: Throwable) {
                log("sendMessage error: ${t.message}")
                Result.failure(Throwable(t.message))
            }
        }
    }

    override suspend fun sendMessage(channelUrl: String, text: String): Result<String> {
        return withContext(IO){
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
