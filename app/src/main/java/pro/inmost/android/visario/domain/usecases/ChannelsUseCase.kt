package pro.inmost.android.visario.domain.usecases

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import pro.inmost.android.visario.data.network.chimeapi.channels.model.Channel
import pro.inmost.android.visario.domain.boundaries.ChannelsLocalRepository
import pro.inmost.android.visario.domain.boundaries.ChannelsNetworkRepository

class ChannelsUseCase(
    private val localRepository: ChannelsLocalRepository,
    private val networkRepository: ChannelsNetworkRepository
) {

    fun observeChannels(timeout: Long = 500) = flow{
        val savedChannels = localRepository.getChannels()
        emit(Result.success(savedChannels))

        while(currentCoroutineContext().isActive){
            val response = networkRepository.getChannels()
            emit(response)
            delay(timeout)
        }
    }

    suspend fun saveChannels(channels: List<Channel>){
        localRepository.saveChannels(channels)
    }
}