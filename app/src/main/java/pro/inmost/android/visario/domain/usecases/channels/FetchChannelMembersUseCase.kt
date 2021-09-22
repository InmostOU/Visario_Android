package pro.inmost.android.visario.domain.usecases.channels

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.contact.Contact

interface FetchChannelMembersUseCase {
    fun fetch(channelArn: String): Flow<List<Contact>>
}