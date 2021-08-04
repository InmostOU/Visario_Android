package pro.inmost.android.visario.domain.usecases.channels

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import pro.inmost.android.visario.domain.repositories.ChannelsLocalRepository
import pro.inmost.android.visario.domain.repositories.ChannelsNetworkRepository
import pro.inmost.android.visario.ui.utils.log

class ObserveChannelsUseCaseImpl(
    private val localRepository: ChannelsLocalRepository,
    private val networkRepository: ChannelsNetworkRepository
): ObserveChannelsUseCase {
    private val timeout = 100L

    override fun observeChannels() = flow {
        val savedChannels = localRepository.getChannels()
        if (savedChannels.isNotEmpty()){
            emit(savedChannels)
        }

        while(currentCoroutineContext().isActive){
            networkRepository.getChannels().onSuccess { channels ->
                emit(channels)
            }.onFailure {
                log(it.message)
            }
            delay(timeout)
        }
    }

    override fun observeChannel(channel: String) = flow {
        localRepository.getChannel(channel)?.let { channel ->
            emit(channel)
        }

       /* while(currentCoroutineContext().isActive){
            networkRepository.getChannel(channel).onSuccess {
                emit(it)
            }.onFailure {
                log(it.message)
            }
            delay(timeout)
        }*/

        networkRepository.getChannel(channel).onSuccess {
            emit(it)
        }.onFailure {
            log(it.message)
        }
        delay(timeout)
    }
}