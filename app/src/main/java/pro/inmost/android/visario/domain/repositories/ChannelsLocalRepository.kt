package pro.inmost.android.visario.domain.repositories

import pro.inmost.android.visario.domain.entities.Channel

interface ChannelsLocalRepository {
    suspend fun getChannels(): List<Channel>
    suspend fun getChannel(channelUrl: String): Channel?
    suspend fun saveChannels(vararg channels: Channel)
}