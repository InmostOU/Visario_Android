package pro.inmost.android.visario.core.data.repositories

import pro.inmost.android.visario.core.data.chimeapi.ChimeApi
import pro.inmost.android.visario.core.data.chimeapi.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.core.data.chimeapi.channels.model.Channel
import pro.inmost.android.visario.core.data.chimeapi.channels.model.ChannelResponseBody
import pro.inmost.android.visario.ui.boundaries.ChatsRepository

class ChatsRepositoryImpl(private val api: ChimeApi): ChatsRepository<List<Channel>> {

    override suspend fun getChats(): Result<List<Channel>> {
        return try {
            val response = api.channels.getChannels()
            if (response.status == STATUS_OK){
                Result.success(response.channels)
            } else {
                Result.failure(Throwable(response.message))
            }
        }catch (e: Throwable){
            Result.failure(e)
        }
    }
}
