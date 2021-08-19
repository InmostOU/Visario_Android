package pro.inmost.android.visario.domain.usecases.channels

import pro.inmost.android.visario.domain.entities.contact.Contact
import pro.inmost.android.visario.domain.repositories.ChannelsRepository

class AddMemberToChannelUseCaseImpl(private val repository: ChannelsRepository) : AddMemberToChannelUseCase {
    override suspend fun invite(channelUrl: String, contact: Contact): Result<Unit> {
       return repository.addMember(channelUrl, contact)
    }
}