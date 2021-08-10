package pro.inmost.android.visario.data.api.services.messages

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.api.dto.requests.MessageRequest
import pro.inmost.android.visario.data.api.utils.logError
import pro.inmost.android.visario.data.api.utils.logInfo

class MessagesManager(
    private val service: MessagingService
) {

    suspend fun getMessages(channelArn: String) = withContext(IO){
        kotlin.runCatching {
            val response = service.getMessages(channelArn)
            if (response.status == STATUS_OK){
                val messages = response.data.sortedByDescending { it.createdTimestamp }
                Result.success(messages)
            } else {
                val message = "${response.status}: ${response.message}"
                logError(message)
                Result.failure(Throwable(message))
            }
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
            val response = service.sendMessage(request)
            logInfo(response.toString())
            if (response.status == STATUS_OK){
                Result.success(Unit)
            } else {
                val message = "${response.status}: ${response.message}"
                logError(message)
                Result.failure(Throwable(message))
            }
        }.getOrElse  {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }

}