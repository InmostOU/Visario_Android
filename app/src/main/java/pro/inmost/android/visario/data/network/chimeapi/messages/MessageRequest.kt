package pro.inmost.android.visario.data.network.chimeapi.messages

data class MessageRequest (
    val content: String,
    val channelArn: String,
    val metadata: String = ""
)
