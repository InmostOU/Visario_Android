package pro.inmost.android.visario.data.entities.message

import pro.inmost.android.visario.domain.entities.message.Message
import pro.inmost.android.visario.domain.entities.message.MessageStatus

fun Message.toDataMessage() = MessageData(
    messageId = this.messageId,
    content = this.text,
    createdTimestamp = this.createdTimestamp,
    lastEditedTimestamp = this.lastEditedTimestamp,
    metadata = "",
    redacted = this.redacted,
    senderName = this.senderName,
    senderArn = this.senderUrl,
    type = this.type,
    fromCurrentUser = this.fromCurrentUser,
    channelArn = this.channelUrl,
    readByMe = this.readByMe,
    status = this.status.name
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
    status = if (this.status == null ) MessageStatus.DELIVERED else MessageStatus.valueOf(this.status!!)
)

fun List<MessageData>.toDomainMessages() = map { it.toDomainMessage()}
fun List<Message>.toMessagesData() = map { it.toDataMessage() }