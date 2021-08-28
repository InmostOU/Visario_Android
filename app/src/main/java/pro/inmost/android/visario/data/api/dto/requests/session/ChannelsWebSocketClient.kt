package pro.inmost.android.visario.data.api.dto.requests.session

import okhttp3.*
import okio.ByteString
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.ui.utils.log
import java.nio.charset.Charset

class ChannelsWebSocketClient(private val api: ChimeApi): WebSocketListener() {
    private val client = OkHttpClient()

    suspend fun connect(){
        api.channels.getWebSocketLink().onSuccess { link ->
             log("ws link: $link")
            val request: Request = Request.Builder()
                .url(link)
                .build()
            client.newWebSocket(request, this)
        }
    }

    fun disconnect(){
        client.dispatcher.cancelAll()
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        log("WS onClosed: $reason")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        log("WS onClosing: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        log("WS onFailure: ${response?.body?.string()}")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        log("WS onMessage: $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        log("WS onMessageBytes: ${bytes.string(Charset.defaultCharset())}")
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        log("WS onOpen: ${response.body?.string()}")
    }
}