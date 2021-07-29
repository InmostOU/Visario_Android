package pro.inmost.android.visario.core.data.repositories

import pro.inmost.android.visario.core.data.chimeapi.ChimeApi
import pro.inmost.android.visario.core.data.chimeapi.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.core.data.chimeapi.channels.model.Channel
import pro.inmost.android.visario.core.data.chimeapi.channels.model.ChannelResponseBody
import pro.inmost.android.visario.core.domain.utils.log
import pro.inmost.android.visario.ui.boundaries.ChatsRepository

class ChatsRepositoryImpl(private val api: ChimeApi): ChatsRepository<Channel> {

    override suspend fun getChats(): Result<List<Channel>> {
        return try {
            val response = api.channels.getChannels()
            if (response.status == STATUS_OK){
                response.channels.forEach { channel ->
                    channel.lastMessage = api.messages.getLastMessage(channel.channelArn)
                }
                Result.success(response.channels)
            } else {
                Result.failure(Throwable(response.message))
            }
        }catch (e: Throwable){
            Result.failure(e)
        }
    }
}
