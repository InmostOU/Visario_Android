package pro.inmost.android.visario.data.api.services.channels

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.api.dto.requests.channels.AddMemberToChannelRequest
import pro.inmost.android.visario.data.api.dto.requests.channels.CreateChannelRequest
import pro.inmost.android.visario.data.utils.logError
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
            val request = CreateChannelRequest(
                name = data.name,
                description = data.description,
                privacy = data.privacy,
                mode = data.mode
            )
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

    suspend fun addMember(channelArn: String, userArn: String): Result<Unit> = withContext(IO){
        kotlin.runCatching {
            val request = AddMemberToChannelRequest(
                channelArn = channelArn,
                userArn = userArn
            )
            service.addMember(request).getResult()
        }.getOrElse {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }

    suspend fun getWebSocketLink(): Result<String> = withContext(IO){
        kotlin.runCatching {
            val response = service.getWebSocketLink()
            if (response.status == STATUS_OK){
                Result.success(response.message)
            } else {
                Result.failure(Throwable(response.error))
            }
        }.getOrElse {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }
}