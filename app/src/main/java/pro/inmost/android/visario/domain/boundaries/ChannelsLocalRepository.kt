package pro.inmost.android.visario.domain.boundaries

import pro.inmost.android.visario.data.network.chimeapi.channels.model.Channel

interface ChannelsLocalRepository {
    suspend fun getChannels(): List<Channel>
    suspend fun saveChannels(channels: List<Channel>)
}