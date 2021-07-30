package pro.inmost.android.visario.domain.boundaries

import pro.inmost.android.visario.data.network.chimeapi.channels.model.Channel

interface ChannelsNetworkRepository {
    suspend fun getChannels(): Result<List<Channel>>
}