package pro.inmost.android.visario.core.data.chimeapi.messages

data class MessageRequest (
    val content: String,
    val channelArn: String,
    val metadata: String = ""
)
