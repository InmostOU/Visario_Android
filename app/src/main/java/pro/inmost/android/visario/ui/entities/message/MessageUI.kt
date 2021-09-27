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
    val fromCurrentUser: Boolean = false,
    val readByMe: Boolean = false,
    val type: String = "STANDARD",
    val status: MessageUIStatus = MessageUIStatus.SENDING,
    val isMeetingInvitation: Boolean,
    val attachment: AttachmentUI? = null
): BaseEntity {

    val edited: Boolean
        get() = lastEditedTimestamp > createdTimestamp

    val createdTimeFormat: String
        get() = DateFormat.format("hh:mm", createdTimestamp).toString()

    val createdDateFormat: String
        get() = DateFormat.format("dd-MM-yyyy", createdTimestamp).toString()

    val createdDayFormat: String
        get() = DateFormat.format("EEE", createdTimestamp).toString()

    val editedDateFormat: String
        get() = DateFormat.format("hh:mm", lastEditedTimestamp).toString()

    val hasAttachment: Boolean
        get() = attachment != null && attachment.path.isNotBlank()

    val isImageAttach: Boolean
        get() = attachment != null && attachment.path.isNotBlank() && attachment.type == AttachmentUI.AttachmentTypeUI.IMAGE

    override val baseId: String
        get() = (channelUrl + senderUrl + senderName).hashCode().toString()


}
