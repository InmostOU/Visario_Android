package pro.inmost.android.visario.domain.usecases.messages

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.isActive
import pro.inmost.android.visario.data.utils.launchIO
import pro.inmost.android.visario.domain.entities.Message
import pro.inmost.android.visario.domain.repositories.MessagesRepository

class FetchMessagesUseCaseImpl(private val repository: MessagesRepository) : FetchMessagesUseCase {
    override fun fetch(channel: String): Flow<List<Message>> {
        launchIO { // for test, until web-sockets are implemented
            while (isActive){
                repository.refreshData(channel)
                delay(100)
            }
        }
        return repository.getMessages(channel)
    }
}