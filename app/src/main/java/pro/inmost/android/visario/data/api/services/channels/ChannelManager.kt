package pro.inmost.android.visario.data.api.services.channels

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.dto.requests.CreateChannelRequest
import pro.inmost.android.visario.data.api.utils.logError
import pro.inmost.android.visario.data.entities.channel.ChannelData

class ChannelManager(private val service: ChannelsService) {

    suspend fun getChannels() = withContext(IO){
        kotlin.runCatching {
            service.getChannels().getResult()
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
            service.create(request).getResult()
        }.getOrElse {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }

    suspend fun leave(channelArn: String): Result<Unit> = withContext(IO){
        kotlin.runCatching {
            service.leave(channelArn).getResult()
        }.getOrElse {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }

    suspend fun search(channelName: String): Result<List<ChannelData>> = withContext(IO) {
        kotlin.runCatching {
            service.search(channelName).getResult()
        }.getOrElse {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }
}