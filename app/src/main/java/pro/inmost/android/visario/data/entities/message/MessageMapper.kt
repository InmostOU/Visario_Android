package pro.inmost.android.visario.data.entities.message

import pro.inmost.android.visario.domain.entities.message.Message

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
    read = this.read
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
    read = this.read
)

fun List<MessageData>.toDomainMessages() = map { it.toDomainMessage()}
fun List<Message>.toMessagesData() = map { it.toDataMessage() }