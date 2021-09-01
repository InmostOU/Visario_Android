package pro.inmost.android.visario.domain.usecases.channels

import pro.inmost.android.visario.domain.entities.contact.Contact

interface AddMemberToChannelUseCase {
    suspend fun invite(channelUrl: String, contact: Contact): Result<Unit>
}