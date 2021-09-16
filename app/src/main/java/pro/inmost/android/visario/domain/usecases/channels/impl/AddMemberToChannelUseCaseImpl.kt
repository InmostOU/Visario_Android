package pro.inmost.android.visario.domain.usecases.channels.impl

import pro.inmost.android.visario.domain.repositories.ChannelsRepository
import pro.inmost.android.visario.domain.usecases.channels.AddMemberToChannelUseCase

class AddMemberToChannelUseCaseImpl(private val repository: ChannelsRepository) :
    AddMemberToChannelUseCase {
    override suspend fun invite(channelArn: String, userArn: String): Result<Unit> {
       return repository.addMember(channelArn, userArn)
    }
}