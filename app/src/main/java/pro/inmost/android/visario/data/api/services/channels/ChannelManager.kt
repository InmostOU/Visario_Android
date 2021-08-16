package pro.inmost.android.visario.data.api.services.channels

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.api.dto.requests.CreateChannelRequest
import pro.inmost.android.visario.data.api.utils.logError
import pro.inmost.android.visario.data.entities.channel.ChannelData

class ChannelManager(private val service: ChannelsService) {

    suspend fun getChannels() = withContext(IO){
        kotlin.runCatching {
            val response = service.getChannels()
            if (response.status == STATUS_OK){
                Result.success(response.data)
            } else {
                logError(response.toString())
                Result.failure(Throwable(response.toString()))
            }
        }.getOrElse {
            logError(it.message ?: "")
            Result.failure(it)
        }

    }

    suspend fun update(data: ChannelData): Result<Unit> = withContext(IO){
        Result.failure(Throwable("not implemented"))
    }

    suspend fun create(data: ChannelData): Result<Unit> = withContext(IO){
        kotlin.runCatching {
            val request = CreateChannelRequest(data.name, data.privacy, data.mode)
            val response = service.create(request)
            if (response.status == STATUS_OK){
                Result.success(Unit)
            } else {
                logError(response.toString())
                Result.failure(Throwable(response.toString()))
            }
        }.getOrElse {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }
}