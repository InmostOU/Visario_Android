package pro.inmost.android.visario.ui.entities.message

import pro.inmost.android.visario.domain.entities.message.Attachment
import pro.inmost.android.visario.domain.entities.message.ReceivingMessage

fun ReceivingMessage.toUIMessage() = MessageUI(
    messageId = this.messageId,
    text = this.text,
    channelUrl = this.channelUrl,
    senderUrl = this.senderUrl,
    senderName = this.senderName,
    createdTimestamp = this.createdTimestamp,
    lastEditedTimestamp = this.lastEditedTimestamp,
    type = this.type,
    fromCurrentUser = this.fromCurrentUser,
    readByMe = this.readByMe,
    status = MessageUIStatus.valueOf(this.status.name),
    isMeetingInvitation = this.isMeetingInvitation
)

fun AttachmentUI.toDomainAttachment() = Attachment(
    path = path,
    type = kotlin.runCatching { Attachment.FileType.valueOf(type.name) }.getOrElse { Attachment.FileType.OTHER }
)

fun List<ReceivingMessage>.toUIMessages() = map { it.toUIMessage() }