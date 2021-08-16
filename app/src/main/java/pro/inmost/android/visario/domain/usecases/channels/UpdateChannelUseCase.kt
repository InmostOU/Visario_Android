package pro.inmost.android.visario.domain.usecases.channels

import pro.inmost.android.visario.domain.entities.channel.Channel

interface UpdateChannelUseCase {
    suspend fun update(channel: Channel): Result<Unit>
}