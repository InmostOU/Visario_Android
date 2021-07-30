package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi
import pro.inmost.android.visario.data.network.chimeapi.messages.Message
import pro.inmost.android.visario.data.network.chimeapi.messages.MessageRequest
import pro.inmost.android.visario.domain.boundaries.MessagesNetworkRepository
import pro.inmost.android.visario.domain.utils.log

class MessagesNetworkRepositoryImpl(private val api: ChimeApi) : MessagesNetworkRepository {
    override suspend fun getMessages(channelArn: String): Result<List<Message>> {
        return withContext(Dispatchers.IO){
            try {
                val response = api.messages.getMessages(channelArn)
                log("message response: $response")
                if (response.messages.isNotEmpty()) {
                    val messages = response.messages.filter { !it.content.contains("Шо там?") }
                    Result.success(messages)
                } else {
                    Result.failure(Throwable("Error"))
                }
            } catch (t: Throwable) {
                log("getMessages: ${t.message}")
                Result.failure(t)
            }
        }
    }

    override suspend fun sendMessage(request: MessageRequest): Result<String> {
        return withContext(Dispatchers.IO){
            try {
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