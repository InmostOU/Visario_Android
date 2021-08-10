package pro.inmost.android.visario.data.api.dto.requests

data class MessageRequest (
    val content: String,
    val channelArn: String,
    val metadata: String = ""
)
