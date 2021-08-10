package pro.inmost.android.visario.domain.usecases.channels

import pro.inmost.android.visario.domain.entities.Channel

interface UpdateChannelUseCase {
    suspend fun update(channel: Channel): Result<Unit>
}