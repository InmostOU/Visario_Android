package pro.inmost.android.visario.data.api.dto.requests.session

import okhttp3.*
import okio.ByteString
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.ui.utils.log
import java.nio.charset.Charset

class WebSocketClient(private val api: ChimeApi): WebSocketListener() {


    suspend fun connect(){
        api.session.getSessionEndpoint().onSuccess { endpoint ->
            log("endpoint: $endpoint")
            val sessionRequest = SessionConnectRequest()
            val wsUrl = sessionRequest.signAndGetRequestUrl(endpoint)
            log("WS url: $wsUrl")
            val client = OkHttpClient()
            val request: Request = Request.Builder()
                .url(wsUrl)
                .build()
            client.newWebSocket(request, this)

          //  client.dispatcher.executorService.shutdown()
        }
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