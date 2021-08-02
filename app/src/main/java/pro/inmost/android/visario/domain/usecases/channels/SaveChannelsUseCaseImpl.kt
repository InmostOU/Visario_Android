package pro.inmost.android.visario.domain.usecases.channels

import pro.inmost.android.visario.domain.repositories.ChannelsLocalRepository
import pro.inmost.android.visario.domain.entities.Channel

class SaveChannelsUseCaseImpl(private val repository: ChannelsLocalRepository) : SaveChannelsUseCase {

    override suspend fun save(vararg channels: Channel) {
        repository.saveChannels(*channels)
    }
}