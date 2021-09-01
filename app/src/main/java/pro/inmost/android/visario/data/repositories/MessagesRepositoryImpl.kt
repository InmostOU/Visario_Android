package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.database.dao.MessagesDao
import pro.inmost.android.visario.data.entities.message.MessageData
import pro.inmost.android.visario.data.entities.message.MessageDataStatus
import pro.inmost.android.visario.data.entities.message.toDataMessage
import pro.inmost.android.visario.data.entities.message.toDomainMessages
import pro.inmost.android.visario.data.utils.extensions.launchIO
import pro.inmost.android.visario.domain.entities.message.Message
import pro.inmost.android.visario.domain.repositories.MessagesRepository
import pro.inmost.android.visario.ui.utils.log

class MessagesRepositoryImpl(
    private val api: ChimeApi,
    private val dao: MessagesDao
) : MessagesRepository {

    override fun getMessages(channelUrl: String): Flow<List<Message>> {
        launchIO { refreshData(channelUrl) }
        return dao.getChannelMessages(channelUrl).map { it.toDomainMessages() }
    }

    override suspend fun sendMessage(message: Message) = withContext(IO) {
        val data = message.toDataMessage()
        dao.insert(data)
        api.messages.sendMessage(data).onFailure {
            data.status = MessageDataStatus.FAIL
            dao.update(data)
        }
    }

    private suspend fun updateDatabase(messages: List<MessageData>) {
        messages.forEach {
            val localMsg = dao.get(it.messageId)
            if (localMsg != null) {
                it.readByMe = localMsg.readByMe
                it.status = MessageDataStatus.DELIVERED
                dao.update(it)
            } else {
                it.readByMe = true
                dao.insert(it)
            }
        }
    }

    override suspend fun refreshData(channelArn: String): Unit = withContext(IO) {
        api.messages.getMessages(channelArn).onSuccess { data ->
            updateDatabase(data)
        }
    }

    override suspend fun markAllMessageAsRead(channelArn: String) = withContext(IO) {
        dao.markAllMessagesAsRead(channelArn)
    }

    override suspend fun updateReadStatusForAll(channelArn: String, read: Boolean) = withContext(IO)  {
        log("msg read status update in repo")
        dao.updateReadStatusForAll(channelArn, read)
    }

    override suspend fun updateReadStatus(messageId: String, read: Boolean) = withContext(IO)  {
        dao.updateReadStatus(messageId, read)
    }
}