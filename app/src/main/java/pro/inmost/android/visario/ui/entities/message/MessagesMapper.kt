package pro.inmost.android.visario.ui.entities.message

import pro.inmost.android.visario.domain.entities.message.Message

fun Message.toUIMessage() = MessageUI(
    messageId = this.messageId,
    text = this.text,
    channelUrl = this.channelUrl,
    senderUrl = this.senderUrl,
    senderName = this.senderName,
    createdTimestamp = this.createdTimestamp,
    lastEditedTimestamp = this.lastEditedTimestamp,
    type = this.type,
    redacted = this.redacted,
    fromCurrentUser = this.fromCurrentUser,
    readByMe = this.readByMe,
    status = MessageUIStatus.valueOf(this.status.name),
    isMeetingInvitation = this.isMeetingInvitation
)

fun List<Message>.toUIMessages() = map { it.toUIMessage() }