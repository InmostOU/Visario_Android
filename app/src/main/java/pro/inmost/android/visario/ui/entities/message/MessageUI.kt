package pro.inmost.android.visario.ui.entities.message

import pro.inmost.android.visario.ui.entities.BaseEntity
import pro.inmost.android.visario.utils.extensions.toDateFormat
import pro.inmost.android.visario.utils.extensions.toDayFormat
import pro.inmost.android.visario.utils.extensions.toTimeFormat

data class MessageUI(
    val id: Long,
    val awsId: String,
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

    val hasText: Boolean
        get() = text.isNotBlank()

    val edited: Boolean
        get() = lastEditedTimestamp > createdTimestamp

    val createdTimeFormat: String
        get() = createdTimestamp.toTimeFormat()

    val createdDateFormat: String
        get() = createdTimestamp.toDateFormat()

    val createdDayFormat: String
        get() = createdTimestamp.toDayFormat()

    val editedDateFormat: String
        get() = lastEditedTimestamp.toTimeFormat()

    val hasAttachment: Boolean
        get() = attachment != null && attachment.path.isNotBlank()

    val isImageAttach: Boolean
        get() = attachment != null && attachment.path.isNotBlank() && attachment.type == AttachmentUI.FileType.IMAGE

    val isFileAttach: Boolean
        get() = attachment != null && attachment.path.isNotBlank() && attachment.type == AttachmentUI.FileType.OTHER

    override val baseId: String
        get() = id.toString()

}
