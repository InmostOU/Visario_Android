package pro.inmost.android.visario.domain.usecases.channels

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.isActive
import pro.inmost.android.visario.data.utils.extensions.launchIO
import pro.inmost.android.visario.domain.entities.channel.Channel
import pro.inmost.android.visario.domain.repositories.ChannelsRepository

class FetchChannelsUseCaseImpl(
    private val repository: ChannelsRepository
): FetchChannelsUseCase {

    override fun getChannels(): Flow<List<Channel>> {
        launchIO { // for test, until web-sockets are implemented
            while (isActive){
                repository.refreshData()
                delay(100)
            }
        }
        return repository.getChannels()
    }

    override fun getChannel(channel: String): Flow<Channel> {
        return repository.getChannel(channel)
    }
}