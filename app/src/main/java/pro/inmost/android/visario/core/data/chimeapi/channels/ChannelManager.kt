package pro.inmost.android.visario.core.data.chimeapi.channels

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import pro.inmost.android.visario.core.data.chimeapi.services.ChannelsService

class ChannelManager(private val channelsService: ChannelsService) {

    suspend fun getAll() = channelsService.getChannels().channels

    fun observe(timeoutInMillis: Long = 5000) = flow {
        while (currentCoroutineContext().isActive){
            emit(channelsService.getChannels().channels)
            delay(timeoutInMillis)
        }
    }
}