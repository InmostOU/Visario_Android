package pro.inmost.android.visario.data.api.services.messages

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.dto.requests.MessageRequest
import pro.inmost.android.visario.data.api.utils.logError

class MessagesManager(
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

    suspend fun sendMessage(channelUrl: String, text: String) = withContext(IO){
        kotlin.runCatching {
            val request = MessageRequest(
                channelArn = channelUrl,
                content = text
            )
            service.sendMessage(request).getResult()
        }.getOrElse  {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }

}