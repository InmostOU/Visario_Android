package pro.inmost.android.visario.domain.usecases.channels

import pro.inmost.android.visario.domain.entities.channel.Channel
import pro.inmost.android.visario.domain.repositories.ChannelsRepository

class CreateChannelUseCaseImpl(private val repository: ChannelsRepository) : CreateChannelUseCase {
    override suspend fun create(channel: Channel): Result<Unit> {
        return repository.create(channel)
    }
}