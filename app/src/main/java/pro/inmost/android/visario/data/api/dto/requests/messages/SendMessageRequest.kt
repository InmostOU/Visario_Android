package pro.inmost.android.visario.data.api.dto.requests.messages

data class SendMessageRequest (
    val messageId: String,
    val content: String,
    val channelArn: String,
    val metadata: String = ""
)
