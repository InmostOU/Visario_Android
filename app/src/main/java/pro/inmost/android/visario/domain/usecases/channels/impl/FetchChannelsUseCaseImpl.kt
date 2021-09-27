package pro.inmost.android.visario.domain.usecases.channels.impl

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.channel.Channel
import pro.inmost.android.visario.domain.repositories.ChannelsRepository
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCase

class FetchChannelsUseCaseImpl(
    private val repository: ChannelsRepository
): FetchChannelsUseCase {

    override fun getChannels(): Flow<List<Channel>> {
        return repository.getChannels()
    }

    override suspend fun getChannel(channelArn: String): Result<Channel> {
        return repository.getChannel(channelArn)
    }

    override suspend fun refresh() {
        repository.refreshData()
    }

    override suspend fun search(channel: String): List<Channel> {
        return repository.search(channel)
    }
}