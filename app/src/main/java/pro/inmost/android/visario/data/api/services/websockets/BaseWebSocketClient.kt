package pro.inmost.android.visario.data.api.services.websockets

import kotlinx.coroutines.delay
import okhttp3.*
import pro.inmost.android.visario.utils.extensions.launchMain
import pro.inmost.android.visario.utils.log
import pro.inmost.android.visario.utils.logE

abstract class BaseWebSocketClient {
    private val client: OkHttpClient = OkHttpClient()
    private val listener = WSListener()
    var connected: Boolean = false
    var reconnecting: Boolean = false

    open val logTag = "WebSocket"

    open var reconnectionDelayMillis = 1000L

    /**
     * Connect to web-socket
     *
     */
    suspend fun connect(){
        val request = Request.Builder()
            .url(getWebSocketLink())
            .build()

        client.newWebSocket(request, listener)
    }

    private fun reconnect() {
        reconnecting = true
        launchMain {
            while (!connected) {
                connect()
                delay(reconnectionDelayMillis)
            }
        }
    }

    /**
     * Disconnect to web-socket
     *
     */

    fun disconnect(){
        client.dispatcher.cancelAll()
    }

    abstract suspend fun getWebSocketLink(): String

    abstract fun onMessageReceived(text: String)

    private inner class WSListener : WebSocketListener(){

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            log("$logTag onClosed: $reason")
            connected = false
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            log("$logTag onClosing: $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            logE("$logTag onFailure: ${t.message}")
            connected = false
            if (!reconnecting){
                reconnect()
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            log("$logTag onMessage: $text")
            onMessageReceived(text)
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            log("$logTag onOpen: ${response.body?.string()}")
            connected = true
            reconnecting = false
            log("$logTag connect")
        }
    }

}