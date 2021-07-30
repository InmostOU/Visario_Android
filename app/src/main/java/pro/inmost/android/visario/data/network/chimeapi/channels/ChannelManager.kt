package pro.inmost.android.visario.data.network.chimeapi.channels

import pro.inmost.android.visario.data.network.chimeapi.services.ChannelsService

class ChannelManager(private val service: ChannelsService) {

    suspend fun getChannels() = service.getChannels()
}