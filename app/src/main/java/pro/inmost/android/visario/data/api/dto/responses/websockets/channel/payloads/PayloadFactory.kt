package pro.inmost.android.visario.data.api.dto.responses.websockets.channel.payloads

import com.google.gson.Gson
import pro.inmost.android.visario.data.api.dto.responses.websockets.channel.ChannelWebSocketMessage
import pro.inmost.android.visario.data.entities.channel.ChannelData
import pro.inmost.android.visario.data.entities.message.MessageData
import pro.inmost.android.visario.data.entities.message.toDataMessage

object PayloadFactory {
    private val gson = Gson()

    fun getChannelMessage(json: String): MessageData {
        val response = gson.fromJson(json, ChannelWebSocketMessage::class.java)
        val messagePayload = gson.fromJson(response.payload, ChannelMessagePayload::class.java)
        return messagePayload.toDataMessage()
    }

    fun getChannelMember(json: String): ChannelData? {
        val response = gson.fromJson(json, CreateChannelMembershipPayload::class.java)
        return null
    }
}