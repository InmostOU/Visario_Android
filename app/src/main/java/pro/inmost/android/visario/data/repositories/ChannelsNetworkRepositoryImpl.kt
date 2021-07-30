package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.network.chimeapi.channels.model.Channel
import pro.inmost.android.visario.domain.boundaries.ChannelsNetworkRepository

class ChannelsNetworkRepositoryImpl(private val api: ChimeApi): ChannelsNetworkRepository{

    override suspend fun getChannels(): Result<List<Channel>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.channels.getChannels()
                if (response.status == STATUS_OK) {
                    response.channels.forEach { channel ->
                        channel.lastMessage = api.messages.getLastMessage(channel.channelArn)
                    }
                    Result.success(response.channels)
                } else {
                    Result.failure(Throwable(response.message))
                }
            } catch (e: Throwable) {
                Result.failure(e)
            }
        }
    }
}
