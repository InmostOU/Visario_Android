package pro.inmost.android.visario.domain.usecases.channels

import pro.inmost.android.visario.domain.entities.Channel

interface CreateChannelUseCase {
   suspend fun create(channel: Channel): Result<Unit>
}