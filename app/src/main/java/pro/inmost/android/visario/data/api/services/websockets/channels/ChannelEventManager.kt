package pro.inmost.android.visario.data.api.services.websockets.channels

import com.google.gson.Gson
import pro.inmost.android.visario.data.api.services.websockets.channels.model.ChannelWebSocketMessage
import pro.inmost.android.visario.utils.extensions.parseJson

/**
 * Helper class for getting EventType from json received by web-socket
 *
 */
object ChannelEventManager {
    private val gson = Gson()

    fun getEvent(json: String): EventType? {
        val response = json.parseJson<ChannelWebSocketMessage>(gson)
        if (response != null){
            val event = response.headers.eventType
            return EventType.valueOf(event)
        }
        return null
    }

    enum class EventType {
        SESSION_ESTABLISHED,
        CREATE_CHANNEL_MESSAGE,
        REDACT_CHANNEL_MESSAGE,
        UPDATE_CHANNEL_MESSAGE,
        DELETE_CHANNEL_MESSAGE,
        UPDATE_CHANNEL,
        DELETE_CHANNEL,
        CREATE_CHANNEL_MEMBERSHIP,
        DELETE_CHANNEL_MEMBERSHIP
    }
}