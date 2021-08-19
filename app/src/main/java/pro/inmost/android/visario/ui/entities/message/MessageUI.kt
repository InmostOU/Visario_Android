package pro.inmost.android.visario.ui.entities.message

import android.text.format.DateFormat
import pro.inmost.android.visario.ui.entities.BaseEntity

data class MessageUI(
    val messageId: String,
    val text: String,
    val channelUrl: String,
    val senderUrl: String,
    val senderName: String,
    val createdTimestamp: Long,
    val lastEditedTimestamp: Long = 0,
    val redacted: Boolean = false,
    val fromCurrentUser: Boolean = false,
    val readByMe: Boolean = false,
    val type: String = "STANDARD",
    val status: MessageUIStatus = MessageUIStatus.SENDING
): BaseEntity(messageId) {

    val createdDateFormat: String
        get() = DateFormat.format("hh:mm", createdTimestamp).toString()

    val editedDateFormat: String
        get() = DateFormat.format("hh:mm", lastEditedTimestamp).toString()
}
