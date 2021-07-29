package pro.inmost.android.visario.core.data.repositories

import pro.inmost.android.visario.core.data.chimeapi.ChimeApi
import pro.inmost.android.visario.core.data.chimeapi.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.core.data.chimeapi.messages.Message
import pro.inmost.android.visario.core.data.chimeapi.messages.MessageRequest
import pro.inmost.android.visario.core.domain.utils.log
import pro.inmost.android.visario.ui.boundaries.MessagesRepository

class MessagesRepositoryImpl(private val api: ChimeApi) : MessagesRepository {
    override suspend fun getMessages(channelArn: String): Result<List<Message>> {
        return try {
            val response = api.messages.getMessages(channelArn)
            log(response.toString())
            if (response.messages.isNotEmpty()) {
                Result.success(response.messages)
            } else {
                Result.failure(Throwable("Error"))
            }
        } catch (t: Throwable) {
            log("getMessages: ${t.message}")
            Result.failure(t)
        }
    }

    override suspend fun sendMessage(request: MessageRequest): Result<String> {
        return try {
            val response = api.messages.sendMessage(request)
            if (response.content.isNotBlank()) {
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