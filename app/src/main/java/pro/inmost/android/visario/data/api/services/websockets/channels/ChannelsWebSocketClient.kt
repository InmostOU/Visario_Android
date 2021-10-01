package pro.inmost.android.visario.data.api.services.websockets.channels

import kotlinx.coroutines.delay
import okhttp3.*
import okio.ByteString
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.dto.responses.websockets.channel.payloads.PayloadFactory
import pro.inmost.android.visario.data.api.services.websockets.channels.ChannelEventManager.EventType
import pro.inmost.android.visario.data.database.dao.ContactsDao
import pro.inmost.android.visario.data.database.dao.MessagesDao
import pro.inmost.android.visario.data.database.dao.ProfileDao
import pro.inmost.android.visario.utils.extensions.launchIO
import pro.inmost.android.visario.utils.extensions.launchMain
import pro.inmost.android.visario.utils.log
import java.nio.charset.Charset

/**
 * Web socket client for receiving realtime channel events
 *
 */
class ChannelsWebSocketClient(
    private val api: ChimeApi,
    private val messagesDao: MessagesDao,
    private val profileDao: ProfileDao,
    private val contactsDao: ContactsDao
) : WebSocketListener() {
    private val client = OkHttpClient()
    private var webSocketLink: String = ""
    private var connected: Boolean = false
    private var reconnecting: Boolean = false


    /**
     * Connect to web-socket
     *
     */
    suspend fun connect() {
        api.channels.getWebSocketLink().onSuccess { link ->
            webSocketLink = link
            val request: Request = Request.Builder()
                .url(webSocketLink)
                .build()
            client.newWebSocket(request, this)
            connected = true
            reconnecting = false
            log("WS connect")
        }
    }

    private fun reconnect() {
        reconnecting = true
        launchMain {
            while (!connected) {
                connect()
                delay(1000)
            }
        }
    }

    /**
     * Disconnect to web-socket
     *
     */
    fun disconnect() {
        client.dispatcher.cancelAll()
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        log("WS onClosed: $reason")
        connected = false
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        log("WS onClosing: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        log("WS onFailure: ${t.message}")
        connected = false
        if (!reconnecting){
            reconnect()
        }
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        log("WS onMessage: $text")
        kotlin.runCatching {
            when (ChannelEventManager.getEvent(text)) {
                EventType.CREATE_CHANNEL_MESSAGE -> insertNewMessage(text)
                EventType.UPDATE_CHANNEL_MESSAGE -> updateMessage(text)
                EventType.DELETE_CHANNEL_MESSAGE -> deleteMessage(text)
                else -> {}
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        log("WS onMessageBytes: ${bytes.string(Charset.defaultCharset())}")
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        log("WS onOpen: ${response.body?.string()}")
    }

    private fun deleteMessage(json: String) =
        launchIO {
            PayloadFactory.getChannelMessage(json)?.let { message ->
                messagesDao.deleteById(message.awsId)
            }
        }

    private fun updateMessage(json: String) =
        launchIO {
            PayloadFactory.getChannelMessage(json)?.let { message ->
                messagesDao.updateContent(
                    awsId = message.awsId,
                    content = message.content?.trim() ?: "",
                    editedTimestamp = message.lastEditedTimestamp
                )
            }
        }

    private fun insertNewMessage(json: String) =
        launchIO {
            PayloadFactory.getChannelMessage(json)?.let { message ->
                log("msg: $message")
                profileDao.get()?.let { profile ->
                    if (profile.userArn == message.senderArn) {
                        message.fromCurrentUser = true
                    }
                }
                messagesDao.upsert(message)
            }
        }
}