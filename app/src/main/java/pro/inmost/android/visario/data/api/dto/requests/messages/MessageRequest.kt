package pro.inmost.android.visario.data.api.dto.requests.messages

data class MessageRequest (
    val content: String,
    val channelArn: String,
    val metadata: String = ""
)
