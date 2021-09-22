package pro.inmost.android.visario.domain.entities.message

data class ReceivingMessage(
    val messageId: String,
    val text: String,
    val channelUrl: String,
    val senderUrl: String,
    val senderName: String,
    val createdTimestamp: Long,
    val type: String,
    val redacted: Boolean = false,
    val lastEditedTimestamp: Long = 0,
    val fromCurrentUser: Boolean = false,
    val readByMe: Boolean = false,
    val metadata: String = "",
    val status: MessageStatus = MessageStatus.SENDING,
    val isMeetingInvitation: Boolean
)