package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.dto.requests.messages.AttachmentData
import pro.inmost.android.visario.data.api.dto.requests.messages.EditMessageRequest
import pro.inmost.android.visario.data.database.dao.MessagesDao
import pro.inmost.android.visario.data.database.dao.ProfileDao
import pro.inmost.android.visario.data.entities.message.MessageData
import pro.inmost.android.visario.data.entities.message.MessageDataStatus
import pro.inmost.android.visario.data.entities.message.toDomainMessages
import pro.inmost.android.visario.data.utils.extensions.toJson
import pro.inmost.android.visario.domain.entities.message.ReceivingMessage
import pro.inmost.android.visario.domain.entities.message.SendingMessage
import pro.inmost.android.visario.domain.repositories.MessagesRepository
import pro.inmost.android.visario.utils.extensions.launchIO
import java.io.File
import java.util.*

class MessagesRepositoryImpl(
    private val api: ChimeApi,
    private val dao: MessagesDao,
    private val profileDao: ProfileDao
) : MessagesRepository {

    override fun getMessages(channelArn: String): Flow<List<ReceivingMessage>> {
        launchIO { refreshData(channelArn) }
        return dao.getChannelMessages(channelArn).map { it.toDomainMessages() }
    }

    override suspend fun sendMessage(message: SendingMessage) = withContext(IO) {
        val data = createMessage(message)
        data.id = dao.insert(data)
        val file = if (message.attachment != null){
            File(message.attachment.path)
        } else null
        send(data, file)
    }

    override suspend fun resendMessage(messageId: String): Result<Unit> = withContext(IO) {
        dao.updateSendingStatus(messageId, MessageDataStatus.SENDING)
        val message = dao.getByAwsId(messageId)
        if (message != null) send(message)
        else Result.failure(Throwable("Resend message error"))
    }

    private suspend fun send(message: MessageData, attachment: File? = null) =
        api.messages.sendMessage(message, attachment).onFailure {
            message.status = MessageDataStatus.FAIL
            dao.update(message)
        }

    override suspend fun edit(
        messageId: String,
        content: String,
        channelArn: String
    ): Result<Unit> = withContext(IO) {
        val request = EditMessageRequest(
            channelArn = channelArn,
            content = content,
            messageId = messageId
        )
        api.messages.editMessage(request)
    }

    override suspend fun delete(messageId: String): Result<Unit> {
        return api.messages.deleteMessage(messageId).onSuccess {
            dao.deleteByAwsId(messageId)
        }
    }

    override suspend fun deleteLocal(messageId: String) = withContext(IO) {
        dao.deleteByAwsId(messageId)
    }

    private suspend fun createMessage(message: SendingMessage): MessageData {
        val profile = profileDao.get()
        return MessageData(
            awsId = UUID.randomUUID().toString(),
            content = message.content,
            channelArn = message.channelArn,
            senderArn = profile?.userArn ?: "",
            senderName = profile?.fullName ?: "",
            createdTimestamp = System.currentTimeMillis(),
            fromCurrentUser = true,
            readByMe = true,
            status = MessageDataStatus.SENDING
        ).also {
            it.metadata = AttachmentData(
                messageId = it.awsId,
                url = message.attachment?.path ?: "",
                fileName = message.attachment?.name ?: "",
                fileType = message.attachment?.extension ?: ""
            ).toJson()
        }
    }

    override suspend fun refreshData(channelArn: String): Unit = withContext(IO) {
        api.messages.getMessages(channelArn).onSuccess { data ->
            dao.updateChannelMessages(channelArn, data)
        }
    }

    private suspend fun updateDatabase(channelArn: String, messages: List<MessageData>) {
         messages.forEach {
             val localMsg = dao.getByAwsId(it.awsId) ?: dao.getByAwsId(it.attachment?.messageId ?: "")
             if (localMsg != null) {
                 if (localMsg.awsId != it.awsId) {
                     dao.updateAwsId(localMsg.awsId, it.awsId)
                 }
                 it.readByMe = localMsg.readByMe
                 it.status = MessageDataStatus.DELIVERED
                 dao.upsert(it)
             } else {
                 it.readByMe = true
                 dao.insert(it)
             }
        }
    }

    override suspend fun markAllMessageAsRead(channelArn: String) = withContext(IO) {
        dao.markAllMessagesAsRead(channelArn)
    }

    override suspend fun updateReadStatusForAll(channelArn: String, read: Boolean) =
        withContext(IO) {
            dao.updateReadStatusForAll(channelArn, read)
        }

    override suspend fun updateReadStatus(messageId: String, read: Boolean) = withContext(IO) {
        dao.updateReadStatus(messageId, read)
    }
}