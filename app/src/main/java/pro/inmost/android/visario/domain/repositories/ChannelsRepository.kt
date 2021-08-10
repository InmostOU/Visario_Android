package pro.inmost.android.visario.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.Channel
import pro.inmost.android.visario.domain.entities.Message

interface ChannelsRepository {
    fun getChannels(): Flow<List<Channel>>
    fun getChannel(channelUrl: String): Flow<Channel>
    suspend fun update(channel: Channel): Result<Unit>
    suspend fun refreshData()
}