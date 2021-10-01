package pro.inmost.android.visario.data.entities.message

import pro.inmost.android.visario.data.api.dto.requests.messages.AttachmentData
import pro.inmost.android.visario.data.api.dto.responses.websockets.channel.payloads.ChannelMessagePayload
import pro.inmost.android.visario.domain.entities.message.Attachment
import pro.inmost.android.visario.domain.entities.message.MessageStatus
import pro.inmost.android.visario.domain.entities.message.ReceivingMessage
import pro.inmost.android.visario.utils.DateParser
import pro.inmost.android.visario.utils.extensions.isMeetingInvitation
import pro.inmost.android.visario.utils.extensions.toMimeType


fun ChannelMessagePayload.toDataMessage() = MessageData(
    awsId = this.MessageId,
    content = this.Content?.trim() ?: "",
    createdTimestamp = DateParser.parseToMillis(this.CreatedTimestamp),
    lastEditedTimestamp = DateParser.parseToMillis(this.LastUpdatedTimestamp),
    metadata = Metadata?.replace("\u0026", "&") ?: "",
    redacted = this.Redacted,
    senderName = this.Sender.Name,
    senderArn = this.Sender.Arn,
    type = this.Type,
    channelArn = this.ChannelArn
)

fun MessageData.toDomainMessage() = ReceivingMessage(
    id = id,
    awsId = this.awsId,
    text = this.content ?: "",
    channelUrl = this.channelArn,
    senderUrl = this.senderArn,
    senderName = this.senderName,
    createdTimestamp = this.createdTimestamp,
    lastEditedTimestamp = this.lastEditedTimestamp,
    type = this.type,
    redacted = this.redacted,
    fromCurrentUser = this.fromCurrentUser,
    readByMe = this.readByMe,
    status = kotlin.runCatching { MessageStatus.valueOf(this.status!!) }.getOrElse { MessageStatus.DELIVERED },
    attachment = attachment?.toAttachment(),
    isMeetingInvitation = content?.isMeetingInvitation() ?: false
)

fun AttachmentData.toAttachment() = Attachment(
    path = url ?: "",
    name = fileName,
    type = getFileType(fileType),
    extension = fileType
)

private fun getFileType(fileType: String): Attachment.FileType {
    return kotlin.runCatching {
        Attachment.FileType.getFromMimeType(fileType.toMimeType()!!)
    }.getOrElse { Attachment.FileType.OTHER }
}

fun List<MessageData>.toDomainMessages() = map { it.toDomainMessage() }.sortedByDescending { it.createdTimestamp }