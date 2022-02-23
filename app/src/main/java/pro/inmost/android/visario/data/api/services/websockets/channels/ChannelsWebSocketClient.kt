package pro.inmost.android.visario.data.api.services.websockets.channels

import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.services.websockets.BaseWebSocketClient
import pro.inmost.android.visario.data.api.services.websockets.channels.model.payloads.PayloadFactory
import pro.inmost.android.visario.data.database.dao.MessagesDao
import pro.inmost.android.visario.data.database.dao.ProfileDao
import pro.inmost.android.visario.utils.extensions.launchIO
import pro.inmost.android.visario.utils.logE

/**
 * Web socket client for receiving realtime channel events
 *
 */
class ChannelsWebSocketClient(
    private val api: ChimeApi,
    private val messagesDao: MessagesDao,
    private val profileDao: ProfileDao
) : BaseWebSocketClient() {

    override val logTag: String = "ChannelsWebSocket"

    override suspend fun getWebSocketLink(): String {
        return api.channels.getWebSocketLink().onFailure {
            logE("Get Cnannels WebSocket link failed: ${it.localizedMessage}")
        }.getOrElse { "" }
    }


    override fun onMessageReceived(text: String) {
        kotlin.runCatching {
            launchIO {
                when (ChannelEventManager.getEvent(text)) {
                    ChannelEventManager.EventType.CREATE_CHANNEL_MESSAGE -> insertNewMessage(text)
                    ChannelEventManager.EventType.UPDATE_CHANNEL_MESSAGE -> updateMessage(text)
                    ChannelEventManager.EventType.DELETE_CHANNEL_MESSAGE -> deleteMessage(text)
                    else -> {}
                }
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    private suspend fun deleteMessage(json: String) =
        PayloadFactory.getChannelMessage(json)?.let { message ->
            messagesDao.deleteByAwsId(message.awsId)
        }

    private suspend fun updateMessage(json: String) =
        PayloadFactory.getChannelMessage(json)?.let { message ->
            messagesDao.updateContent(
                awsId = message.awsId,
                content = message.content?.trim() ?: "",
                editedTimestamp = message.lastEditedTimestamp
            )
        }

    private suspend fun insertNewMessage(json: String) =
        PayloadFactory.getChannelMessage(json)?.let { message ->
            profileDao.get()?.let { profile ->
                if (profile.userArn == message.senderArn) {
                    message.fromCurrentUser = true
                }
            }
            messagesDao.upsert(message)
        }
}