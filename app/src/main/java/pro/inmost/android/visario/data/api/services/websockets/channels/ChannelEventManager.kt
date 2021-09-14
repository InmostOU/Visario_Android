package pro.inmost.android.visario.data.api.services.websockets.channels

import com.google.gson.Gson
import pro.inmost.android.visario.data.api.dto.responses.websockets.channel.ChannelWebSocketMessage

object ChannelEventManager {
    private val gson = Gson()

    fun getEvent(json: String): EventType {
        val response = gson.fromJson(json, ChannelWebSocketMessage::class.java)
        val event = response.headers.eventType
        return EventType.valueOf(event)
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