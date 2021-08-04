package pro.inmost.android.visario.domain.usecases.channels

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.Channel

interface ObserveChannelsUseCase {
    fun observeChannels(): Flow<List<Channel>>
    fun observeChannel(channel: String): Flow<Channel>
}