package pro.inmost.android.visario.data.api.services.websockets.channels

import okhttp3.*
import okio.ByteString
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.dto.responses.websockets.channel.payloads.PayloadFactory
import pro.inmost.android.visario.data.database.dao.MessagesDao
import pro.inmost.android.visario.data.utils.extensions.launchIO
import pro.inmost.android.visario.data.utils.logInfo
import java.nio.charset.Charset

class ChannelsWebSocketClient(
    private val api: ChimeApi,
    private val messagesDao: MessagesDao
) : WebSocketListener() {
    private val client = OkHttpClient()

    suspend fun connect() {
        api.channels.getWebSocketLink().onSuccess { link ->
            val request: Request = Request.Builder()
                .url(link)
                .build()
            client.newWebSocket(request, this)
        }
    }

    fun disconnect() {
        client.dispatcher.cancelAll()
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        logInfo("WS onClosed: $reason")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        logInfo("WS onClosing: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        logInfo("WS onFailure: ${response?.body?.string()}")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        logInfo("WS onMessage: $text")
        kotlin.runCatching {
            when (ChannelEventManager.getEvent(text)) {
                ChannelEventManager.EventType.CREATE_CHANNEL_MESSAGE -> {
                    val message = PayloadFactory.getChannelMessage(text)
                    launchIO {
                        messagesDao.upsert(message)
                    }
                }
                else -> {}
            }
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        logInfo("WS onMessageBytes: ${bytes.string(Charset.defaultCharset())}")
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        logInfo("WS onOpen: ${response.body?.string()}")
    }
}