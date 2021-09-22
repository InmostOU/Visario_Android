package pro.inmost.android.visario.ui.entities.message

import pro.inmost.android.visario.domain.entities.message.Attachment
import pro.inmost.android.visario.domain.entities.message.MessageStatus
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

fun MessageUI.toDomainMessage() = ReceivingMessage(
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
    status = MessageStatus.valueOf(this.status.name),
    isMeetingInvitation = this.isMeetingInvitation
)

fun AttachmentUI.toDomainAttachment() = Attachment(
    path = uri.path ?: "",
    type = kotlin.runCatching { Attachment.AttachmentType.valueOf(type.name) }.getOrElse { Attachment.AttachmentType.OTHER }
)

fun List<ReceivingMessage>.toUIMessages() = map { it.toUIMessage() }