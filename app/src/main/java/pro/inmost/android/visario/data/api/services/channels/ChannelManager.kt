package pro.inmost.android.visario.data.api.services.channels

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.api.dto.requests.channels.AddMemberToChannelRequest
import pro.inmost.android.visario.data.api.dto.requests.channels.CreateChannelRequest
import pro.inmost.android.visario.data.entities.channel.ChannelData
import pro.inmost.android.visario.data.utils.logError

/**
 * Channel manager for interaction with the channels
 *
 * @property service - channels service generated by retrofit
 *
 */
class ChannelManager(private val service: ChannelsService) {


    /**
     * Get list of [ChannelData] the user is a member of
     *
     *  @return [Result] that encapsulates list of [ChannelData]
     * or a failure with an arbitrary [Throwable] exception.
     */
    suspend fun getChannels() = withContext(IO){
        kotlin.runCatching {
            Result.success(service.getChannels().data)
        }.getOrElse {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }


    /**
     * Update the channel (not implemented yet)
     *
     * @param data [ChannelData]
     * @return [Result]
     */
    suspend fun update(data: ChannelData): Result<Unit> = withContext(IO){
        Result.failure(Throwable("not implemented"))
    }

    /**
     * Create new channel (public or private)
     *
     * @param data [ChannelData]
     * @return [Result]
     */
    suspend fun create(data: ChannelData): Result<Unit> = withContext(IO){
        kotlin.runCatching {
            val request = CreateChannelRequest(
                name = data.name,
                description = data.description ?: "",
                privacy = data.privacy,
                mode = data.mode
            )
            service.create(request).getResult()
        }.getOrElse {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }

    /**
     * Leave and delete channel from user list
     *
     * @param channelArn
     * @return [Result]
     */
    suspend fun leave(channelArn: String): Result<Unit> = withContext(IO){
        kotlin.runCatching {
            service.leave(channelArn).getResult()
        }.getOrElse {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }


    /**
     * Search channels by name
     *
     * @param channelName name of channel
     * @return [Result] that encapsulates [ChannelData]
     * or a failure with an arbitrary [Throwable] exception
     */
    suspend fun search(channelName: String): Result<List<ChannelData>> = withContext(IO) {
        kotlin.runCatching {
            service.search(channelName).getResult()
        }.getOrElse {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }


    /**
     * Add member to the specified channel
     *
     * @param channelArn channel url from AWS Chime
     * @param userArn user url from AWS Chime
     * @return [Result]
     */
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


    /**
     * Get signed link for channels web-socket client
     *
     * that encapsulates link for use in channels web-socket client
     * or a failure with an arbitrary [Throwable] exception.
     */
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