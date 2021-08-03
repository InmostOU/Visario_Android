package pro.inmost.android.visario.data.network.utils

import pro.inmost.android.visario.data.entities.ChannelData
import pro.inmost.android.visario.data.entities.ChannelWithMessages
import pro.inmost.android.visario.data.entities.MessageData
import pro.inmost.android.visario.data.network.chimeapi.auth.model.RegistrationRequest
import pro.inmost.android.visario.domain.entities.*

fun ChannelData.toDomainChannel(messages: List<MessageData>): Channel? {
    return kotlin.runCatching {
        Channel(
            url = channelArn,
            name = this.name,
            mode = ChannelMode.valueOf(this.mode ?: "UNRESTRICTED"),
            privacy = Privacy.valueOf(this.privacy ?: "PUBLIC"),
            messages = messages.toDomainMessages()
        )
    }.getOrNull()
}

fun List<Channel>.toChannelsWithMessages(): List<ChannelWithMessages> {
    return mapNotNull { channel ->
        channel.toChannelWithMessages()
    }
}

fun Channel.toChannelWithMessages(): ChannelWithMessages? {
    return kotlin.runCatching {
        ChannelWithMessages (
            channel = this.toChannelData(),
            messages = this.messages.toMessagesData()
        )
    }.getOrNull()
}

fun List<Message>.toMessagesData(): List<MessageData> {
    return map { message ->
        message.toMessageData()
    }
}

fun Message.toMessageData(): MessageData {
    return MessageData(
        messageId = this.messageId,
        content = this.text,
        createdTimestamp = this.createdTimestamp,
        lastEditedTimestamp = this.lastEditedTimestamp.toString(),
        metadata = "",
        redacted = this.redacted,
        senderName = this.senderName,
        senderArn = this.senderUrl,
        type = this.type,
        fromCurrentUser = this.fromCurrentUser,
        channelArn = this.channelUrl
    )
}

fun Channel.toChannelData(): ChannelData {
    return ChannelData(
        channelArn = this.url,
        name = this.name,
        mode = this.mode.name,
        privacy = this.privacy.name,
        metadata = ""
    )
}

fun List<MessageData>.toDomainMessages(): List<Message>{
    return mapNotNull { data ->
        data.toDomainMessage()
    }
}

fun MessageData.toDomainMessage(): Message? {
    return  kotlin.runCatching {
        Message(
            messageId = this.messageId,
            text = this.content,
            channelUrl = this.channelArn,
            senderUrl = this.senderArn,
            senderName = this.senderName,
            createdTimestamp = this.createdTimestamp,
            type = this.type,
            redacted = this.redacted
        )
    }.getOrNull()
}

fun List<ChannelWithMessages>.toDomainChannels(): List<Channel> {
    return mapNotNull { it.toDomainChannel() }
}

fun ChannelWithMessages.toDomainChannel(): Channel? {
    return kotlin.runCatching {
        Channel(
            url = channel.channelArn,
            name = channel.name,
            mode = ChannelMode.valueOf(channel.mode ?: "UNRESTRICTED"),
            privacy = Privacy.valueOf(channel.privacy ?: "PUBLIC"),
            messages = this.messages.toDomainMessages()
        )
    }.getOrNull()
}

fun User.toRegistrationRequest(): RegistrationRequest{
    return RegistrationRequest(
        username = this.username,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        birthday = this.dateOfBirth,
        password = this.password,
        matchingPassword = this.password
    )
}