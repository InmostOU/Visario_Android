package pro.inmost.android.visario.domain.entities.message

data class SendingMessage(
    val channelArn: String,
    val content: String,
    val attachment: Attachment? = null
)
