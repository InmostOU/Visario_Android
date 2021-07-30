package pro.inmost.android.visario.domain.usecases

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import pro.inmost.android.visario.data.network.chimeapi.messages.Message
import pro.inmost.android.visario.data.network.chimeapi.messages.MessageRequest
import pro.inmost.android.visario.domain.boundaries.MessagesLocalRepository
import pro.inmost.android.visario.domain.boundaries.MessagesNetworkRepository

class MessagingUseCase(
    private val localRepository: MessagesLocalRepository,
    private val networkRepository: MessagesNetworkRepository
) {

    fun observeMessages(channelArn: String, timeout: Long = 100) = flow{
        /*val savedMessages = localRepository.getMessages(channelArn)
        emit(Result.success(savedMessages))*/

        while (currentCoroutineContext().isActive){
            val response = networkRepository.getMessages(channelArn)
            emit(response)
            delay(timeout)
        }
    }

    suspend fun saveMessage(list: List<Message>){
        localRepository.saveMessages(list)
    }

    suspend fun sendMessage(message: MessageRequest) = networkRepository.sendMessage(message)
}