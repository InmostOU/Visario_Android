package pro.inmost.android.visario.domain.usecases.channels

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.channel.Channel

interface FetchChannelsUseCase {
    fun getChannels(): Flow<List<Channel>>
    fun getChannel(channel: String): Flow<Channel>
}