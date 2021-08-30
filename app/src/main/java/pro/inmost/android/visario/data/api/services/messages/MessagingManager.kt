package pro.inmost.android.visario.data.api.services.messages

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.dto.requests.messages.SendMessageRequest
import pro.inmost.android.visario.data.entities.message.MessageData
import pro.inmost.android.visario.data.utils.logError

class MessagingManager(
    private val service: MessagingService
) {
    suspend fun getMessages(channelArn: String) = withContext(IO){
        kotlin.runCatching {
             service.getMessages(channelArn).getResult()
        }.getOrElse  {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }

    suspend fun sendMessage(data: MessageData) = withContext(IO){
        kotlin.runCatching {
            val request = SendMessageRequest(
                channelArn = data.channelArn,
                content = data.content
            )
            service.sendMessage(request).getResult()
        }.getOrElse  {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }

}