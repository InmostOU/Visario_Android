package pro.inmost.android.visario.domain.usecases.channels.impl

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.contact.Contact
import pro.inmost.android.visario.domain.repositories.ChannelsRepository
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelMembersUseCase

class FetchChannelMembersUseCaseImpl(private val repository: ChannelsRepository) : FetchChannelMembersUseCase {
    override fun fetch(channelArn: String): Flow<List<Contact>> {
        return repository.getChannelMembers(channelArn)
    }

}