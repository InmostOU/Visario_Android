package pro.inmost.android.visario.data.api.dto.requests.messages

data class EditMessageRequest(
    val messageId: String,
    val content: String,
    val channelArn: String
)
