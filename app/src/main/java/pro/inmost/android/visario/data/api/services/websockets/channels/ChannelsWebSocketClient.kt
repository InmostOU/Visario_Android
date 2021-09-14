package pro.inmost.android.visario.data.api.services.websockets.channels

import kotlinx.coroutines.delay
import okhttp3.*
import okio.ByteString
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.dto.responses.websockets.channel.payloads.PayloadFactory
import pro.inmost.android.visario.data.api.services.websockets.channels.ChannelEventManager.EventType
import pro.inmost.android.visario.data.database.dao.MessagesDao
import pro.inmost.android.visario.data.utils.extensions.launchIO
import pro.inmost.android.visario.data.utils.extensions.launchMain
import pro.inmost.android.visario.ui.utils.log
import java.nio.charset.Charset

class ChannelsWebSocketClient(
    private val api: ChimeApi,
    private val messagesDao: MessagesDao
) : WebSocketListener() {
    private val client = OkHttpClient()
    private var webSocketLink: String = ""
    private var connected: Boolean = false
    private var reconnecting: Boolean = false

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
                EventType.CREATE_CHANNEL_MESSAGE -> {
                    val message = PayloadFactory.getChannelMessage(text)
                    launchIO { messagesDao.upsert(message) }
                }
                EventType.UPDATE_CHANNEL_MESSAGE -> {
                    val message = PayloadFactory.getChannelMessage(text)
                    launchIO {
                        messagesDao.updateContent(
                            messageId = message.messageId,
                            content = message.content,
                            editedTimestamp = message.lastEditedTimestamp
                        )
                    }
                }
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
}