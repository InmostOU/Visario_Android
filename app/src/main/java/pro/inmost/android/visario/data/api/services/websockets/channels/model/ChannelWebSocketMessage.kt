package pro.inmost.android.visario.data.api.services.websockets.channels.model

import com.google.gson.annotations.SerializedName


/**
 * Channel web socket message
 *
 * @property headers include event and message types
 * @property payload - json with main event-info
 *
 */
data class ChannelWebSocketMessage(
    @SerializedName("Headers")
    val headers: Headers,
    @SerializedName("Payload")
    val payload: String
){

    data class Headers(
        @SerializedName("x-amz-chime-event-type")
        val eventType: String,
        @SerializedName("x-amz-chime-message-type")
        val messageType: String
    )
}


