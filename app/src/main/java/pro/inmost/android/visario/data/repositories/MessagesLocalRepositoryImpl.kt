package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.database.dao.MessagesDao
import pro.inmost.android.visario.data.network.chimeapi.messages.Message
import pro.inmost.android.visario.domain.boundaries.MessagesLocalRepository

class MessagesLocalRepositoryImpl(private val dao: MessagesDao) : MessagesLocalRepository {
    override suspend fun getMessages(channelArn: String) = withContext(Dispatchers.IO){
        dao.getAll()
    }

    override suspend fun saveMessages(channels: List<Message>) = withContext(Dispatchers.IO) {
        dao.insert(*channels.toTypedArray())
    }
}