package pro.inmost.android.visario.data.entities.message

import pro.inmost.android.visario.data.api.dto.responses.websockets.channel.payloads.CreateChannelMessagePayload
import pro.inmost.android.visario.domain.entities.message.Message
import pro.inmost.android.visario.domain.entities.message.MessageStatus
import pro.inmost.android.visario.ui.utils.DateParser

fun Message.toDataMessage() = MessageData(
    messageId = this.messageId,
    content = this.text,
    createdTimestamp = this.createdTimestamp,
    lastEditedTimestamp = this.lastEditedTimestamp,
    redacted = this.redacted,
    senderName = this.senderName,
    senderArn = this.senderUrl,
    type = this.type,
    fromCurrentUser = this.fromCurrentUser,
    channelArn = this.channelUrl,
    readByMe = this.readByMe,
    status = this.status.name,
    metadata = this.metadata
)

fun CreateChannelMessagePayload.toDataMessage() = MessageData(
    messageId = this.MessageId,
    content = this.Content,
   // createdTimestamp = DateParser.parseToMillis(this.CreatedTimestamp),
    createdTimestamp = DateParser.parseToMillis(this.CreatedTimestamp),
    lastEditedTimestamp = DateParser.parseToMillis(this.LastUpdatedTimestamp),
    metadata = this.Metadata,
    redacted = this.Redacted,
    senderName = this.Sender.Name,
    senderArn = this.Sender.Arn,
    type = this.Type,
    channelArn = this.ChannelArn
)

fun MessageData.toDomainMessage() = Message(
    messageId = this.messageId,
    text = this.content,
    channelUrl = this.channelArn,
    senderUrl = this.senderArn,
    senderName = this.senderName,
    createdTimestamp = this.createdTimestamp,
    lastEditedTimestamp = this.lastEditedTimestamp,
    type = this.type,
    redacted = this.redacted,
    fromCurrentUser = this.fromCurrentUser,
    readByMe = this.readByMe,
    status = if (this.status == null ) MessageStatus.DELIVERED else MessageStatus.valueOf(this.status!!),
    metadata = this.metadata
)

fun List<MessageData>.toDomainMessages() = map { it.toDomainMessage()}
fun List<Message>.toMessagesData() = map { it.toDataMessage() }