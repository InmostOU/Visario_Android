package pro.inmost.android.visario.ui.entities.message

import pro.inmost.android.visario.domain.entities.message.Attachment
import pro.inmost.android.visario.domain.entities.message.ReceivingMessage

fun ReceivingMessage.toUIMessage() = MessageUI(
    id = id,
    awsId = this.awsId,
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
    isMeetingInvitation = this.isMeetingInvitation,
    attachment = attachment?.toUIAttachment()
)

fun AttachmentUI.toDomainAttachment() = Attachment(
    path = path,
    name = name,
    extension = extension,
    type = kotlin.runCatching { Attachment.FileType.valueOf(type.name) }.getOrElse { Attachment.FileType.OTHER }
)

fun Attachment.toUIAttachment() = AttachmentUI(
    path = path,
    name = name,
    extension = extension,
    type = kotlin.runCatching { AttachmentUI.FileType.valueOf(type.name) }.getOrElse { AttachmentUI.FileType.OTHER }
)

fun List<ReceivingMessage>.toUIMessages() = map { it.toUIMessage() }