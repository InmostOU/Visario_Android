package pro.inmost.android.visario.data.api.dto.requests.messages

data class SendMessageRequest (
    val content: String,
    val channelArn: String,
    val metadata: String = ""
)
