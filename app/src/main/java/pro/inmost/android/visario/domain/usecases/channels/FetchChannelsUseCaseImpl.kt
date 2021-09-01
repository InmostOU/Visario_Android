package pro.inmost.android.visario.domain.usecases.channels

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.channel.Channel
import pro.inmost.android.visario.domain.repositories.ChannelsRepository

class FetchChannelsUseCaseImpl(
    private val repository: ChannelsRepository
): FetchChannelsUseCase {

    override fun getChannels(): Flow<List<Channel>> {
        return repository.getChannels()
    }

    override fun getChannel(channel: String): Flow<Channel> {
        return repository.getChannel(channel)
    }

    override suspend fun refresh() {
        repository.refreshData()
    }

    override suspend fun search(channel: String): List<Channel> {
        return repository.search(channel)
    }
}