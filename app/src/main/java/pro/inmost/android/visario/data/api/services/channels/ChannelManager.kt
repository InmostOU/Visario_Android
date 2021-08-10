package pro.inmost.android.visario.data.api.services.channels

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.api.utils.logError
import pro.inmost.android.visario.data.api.utils.logInfo
import pro.inmost.android.visario.data.entities.ChannelData

class ChannelManager(private val service: ChannelsService) {

    suspend fun getChannels() = withContext(IO){
        kotlin.runCatching {
            val response = service.getChannels()
            logInfo("getChannels response: $response")
            if (response.status == STATUS_OK){
                Result.success(response.data)
            } else {
                val message = "${response.status}: ${response.message}"
                logError(message)
                Result.failure(Throwable(message))
            }
        }.getOrElse {
            logError(it.message ?: "")
            Result.failure(it)
        }

    }

    suspend fun update(data: ChannelData): Result<Unit> = withContext(IO){
        Result.failure(Throwable("not implemented"))
    }
}