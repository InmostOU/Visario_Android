package pro.inmost.android.visario.domain.usecases.channels

import pro.inmost.android.visario.domain.entities.channel.Channel
import pro.inmost.android.visario.domain.repositories.ChannelsRepository

class UpdateChannelUseCaseImpl(private val repository: ChannelsRepository) : UpdateChannelUseCase {

    override suspend fun update(channel: Channel): Result<Unit> {
        return repository.update(channel)
    }
}