package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.database.dao.MessagesDao
import pro.inmost.android.visario.data.entities.message.MessageData
import pro.inmost.android.visario.data.entities.message.toDomainMessages
import pro.inmost.android.visario.data.utils.extensions.launchIO
import pro.inmost.android.visario.domain.entities.message.Message
import pro.inmost.android.visario.domain.repositories.MessagesRepository

class MessagesRepositoryImpl(
    private val api: ChimeApi,
    private val dao: MessagesDao
) : MessagesRepository {

    override fun getMessages(channelUrl: String): Flow<List<Message>> {
        val savedMessages = dao.getChannelMessages(channelUrl).map { it.toDomainMessages() }
        launchIO { refreshData(channelUrl) }
        return savedMessages
    }

    override suspend fun sendMessage(channelUrl: String, text: String) = withContext(IO) {
        api.messages.sendMessage(channelUrl, text)
    }

    private suspend fun updateDatabase(channelArn: String, messages: List<MessageData>) {
        messages.forEach {
            if (dao.isRowIsExist(it.messageId)) it.read = true
        }
        dao.updateChannelMessages(channelArn, messages)
    }

    override suspend fun refreshData(channelArn: String): Unit = withContext(IO) {
        api.messages.getMessages(channelArn).onSuccess { data ->
            updateDatabase(channelArn, data)
        }
    }
}