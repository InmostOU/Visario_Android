package pro.inmost.android.visario.core.data.chimeapi.channels

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import pro.inmost.android.visario.core.data.chimeapi.services.ChannelsService

class ChannelManager(private val service: ChannelsService) {

    suspend fun getChannels() = service.getChannels()
}