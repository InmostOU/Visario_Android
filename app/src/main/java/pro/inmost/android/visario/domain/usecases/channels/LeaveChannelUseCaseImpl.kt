package pro.inmost.android.visario.domain.usecases.channels

import pro.inmost.android.visario.domain.repositories.ChannelsRepository

class LeaveChannelUseCaseImpl(private val repository: ChannelsRepository) : LeaveChannelUseCase {
    override suspend fun leave(channelUrl: String): Result<Unit> {
        return repository.leave(channelUrl)
    }
}