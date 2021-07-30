package pro.inmost.android.visario.data.network.chimeapi.messages

data class SendMessageResponse(
    val channelArn: String,
    val content: String?,
    val metadata: String?
)
