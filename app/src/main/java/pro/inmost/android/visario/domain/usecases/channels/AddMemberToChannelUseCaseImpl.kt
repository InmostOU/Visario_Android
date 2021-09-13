package pro.inmost.android.visario.domain.usecases.channels

import pro.inmost.android.visario.domain.repositories.ChannelsRepository

class AddMemberToChannelUseCaseImpl(private val repository: ChannelsRepository) : AddMemberToChannelUseCase {
    override suspend fun invite(channelArn: String, userArn: String): Result<Unit> {
       return repository.addMember(channelArn, userArn)
    }
}