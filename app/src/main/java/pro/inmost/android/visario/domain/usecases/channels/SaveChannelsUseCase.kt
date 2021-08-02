package pro.inmost.android.visario.domain.usecases.channels

import pro.inmost.android.visario.domain.entities.Channel

interface SaveChannelsUseCase {
    suspend fun save(vararg channels: Channel)
}