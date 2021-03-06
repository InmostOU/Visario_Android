package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.dto.requests.messages.EditMessageRequest
import pro.inmost.android.visario.data.database.dao.MessagesDao
import pro.inmost.android.visario.data.entities.message.MessageData
import pro.inmost.android.visario.data.entities.message.MessageDataStatus
import pro.inmost.android.visario.data.entities.message.toDomainMessages
import pro.inmost.android.visario.data.entities.profile.toDataProfile
import pro.inmost.android.visario.data.utils.extensions.launchIO
import pro.inmost.android.visario.domain.entities.message.Message
import pro.inmost.android.visario.domain.repositories.MessagesRepository
import pro.inmost.android.visario.domain.repositories.ProfileRepository
import java.util.*

class MessagesRepositoryImpl(
    private val api: ChimeApi,
    private val dao: MessagesDao,
    private val profileRepository: ProfileRepository
) : MessagesRepository {

    override fun getMessages(channelArn: String): Flow<List<Message>> {
        launchIO { refreshData(channelArn) }
        return dao.getChannelMessages(channelArn).map { it.toDomainMessages() }
    }

    override suspend fun sendMessage(message: String, channelArn: String) = withContext(IO) {
        val data = createMessage(message, channelArn)
        dao.insert(data)
        send(data)
    }

    override suspend fun resendMessage(messageId: String): Result<Unit> = withContext(IO){
        dao.updateSendingStatus(messageId, MessageDataStatus.SENDING)
        val message = dao.get(messageId)
        if (message != null) send(message)
        else Result.failure(Throwable("Resend message error"))
    }

    private suspend fun send(message: MessageData) = api.messages.sendMessage(message).onFailure {
        message.status = MessageDataStatus.FAIL
        dao.update(message)
    }

    override suspend fun edit(
        messageId: String,
        content: String,
        channelArn: String
    ): Result<Unit> = withContext(IO){
        val request = EditMessageRequest(
            channelArn = channelArn,
            content = content,
            messageId = messageId
        )
        api.messages.editMessage(request)
    }

    override suspend fun delete(messageId: String): Result<Unit> {
        return api.messages.deleteMessage(messageId)
    }

    override suspend fun deleteLocal(messageId: String) = withContext(IO) {
       dao.deleteById(messageId)
    }

    private suspend fun createMessage(content: String, channelArn: String, messageId: String? = null): MessageData {
        val profile = profileRepository.observeProfile().firstOrNull()?.toDataProfile()
        return MessageData(
            messageId = messageId ?: UUID.randomUUID().toString(),
            content = content,
            channelArn = channelArn,
            senderArn = profile?.userArn ?: "",
            senderName = profile?.fullName ?: "",
            createdTimestamp = System.currentTimeMillis(),
            fromCurrentUser = true,
            readByMe = true,
            status = MessageDataStatus.SENDING
        )
    }

    override suspend fun refreshData(channelArn: String): Unit = withContext(IO) {
        api.messages.getMessages(channelArn).onSuccess { data ->
            updateDatabase(data)
        }
    }

    private suspend fun updateDatabase(messages: List<MessageData>) {
        messages.forEach {
            val localMsg = dao.get(it.messageId) ?: dao.get(it.metadata)
            if (localMsg != null) {
                if (localMsg.messageId != it.messageId){
                    dao.updateMessageId(localMsg.messageId, it.messageId)
                }
                it.readByMe = localMsg.readByMe
                it.status = MessageDataStatus.DELIVERED
                dao.update(it)
            } else {
                it.readByMe = true
                dao.insert(it)
            }
        }
    }

    override suspend fun markAllMessageAsRead(channelArn: String) = withContext(IO) {
        dao.markAllMessagesAsRead(channelArn)
    }

    override suspend fun updateReadStatusForAll(channelArn: String, read: Boolean) = withContext(IO)  {
        dao.updateReadStatusForAll(channelArn, read)
    }

    override suspend fun updateReadStatus(messageId: String, read: Boolean) = withContext(IO)  {
        dao.updateReadStatus(messageId, read)
    }
}