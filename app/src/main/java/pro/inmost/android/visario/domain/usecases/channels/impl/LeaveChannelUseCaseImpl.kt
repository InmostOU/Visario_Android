package pro.inmost.android.visario.domain.usecases.channels.impl

import pro.inmost.android.visario.domain.repositories.ChannelsRepository
import pro.inmost.android.visario.domain.usecases.channels.LeaveChannelUseCase

class LeaveChannelUseCaseImpl(private val repository: ChannelsRepository) : LeaveChannelUseCase {
    override suspend fun leave(channelUrl: String): Result<Unit> {
        return repository.leave(channelUrl)
    }
}