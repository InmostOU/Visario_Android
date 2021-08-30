package pro.inmost.android.visario.data.api.dto.responses.websockets.channel

import com.google.gson.annotations.SerializedName

data class ChannelWebSocketMessage(
    @SerializedName("Headers")
    val headers: Headers,
    @SerializedName("Payload")
    val payload: String
)

data class Headers(
    @SerializedName("x-amz-chime-event-type")
    val eventType: String,
    @SerializedName("x-amz-chime-message-type")
    val messageType: String
)