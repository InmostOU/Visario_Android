package pro.inmost.android.visario.domain.usecases.channels.impl

import pro.inmost.android.visario.domain.entities.channel.Channel
import pro.inmost.android.visario.domain.repositories.ChannelsRepository
import pro.inmost.android.visario.domain.usecases.channels.AddMemberToChannelUseCase

class AddMemberToChannelUseCaseImpl(private val repository: ChannelsRepository) :
    AddMemberToChannelUseCase {
    override suspend fun invite(channelArn: String, userArn: String): Result<Channel> {
       return repository.addMember(channelArn, userArn)
    }
}