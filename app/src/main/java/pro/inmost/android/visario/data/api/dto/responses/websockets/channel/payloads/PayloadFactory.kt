package pro.inmost.android.visario.data.api.dto.responses.websockets.channel.payloads

import com.google.gson.Gson
import pro.inmost.android.visario.data.api.dto.responses.websockets.channel.ChannelWebSocketMessage
import pro.inmost.android.visario.data.entities.channel.ChannelData
import pro.inmost.android.visario.data.entities.message.MessageData
import pro.inmost.android.visario.data.entities.message.toDataMessage

/**
 * Helper class for parse json from web-socket's message into DTO
 *
 */
object PayloadFactory {
    private val gson = Gson()


    /**
     * Get channel's message from json
     *
     * @param json from web-socket event's json
     * @return [MessageData] if json correct else null
     */
    fun getChannelMessage(json: String): MessageData? {
        return kotlin.runCatching {
            val response = gson.fromJson(json, ChannelWebSocketMessage::class.java)
            val messagePayload = gson.fromJson(response.payload, ChannelMessagePayload::class.java)
            messagePayload.toDataMessage()
        }.getOrNull()
    }

    fun getChannelMember(json: String): ChannelData? {
        val response = gson.fromJson(json, ChannelMembershipPayload::class.java)
        return null
    }
}