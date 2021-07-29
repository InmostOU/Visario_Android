package pro.inmost.android.visario.core.data.chimeapi.messages

data class SendMessageResponse(
    val channelArn: String,
    val content: String,
    val metadata: String
)
