package pro.inmost.android.visario.data.entities.channel

import pro.inmost.android.visario.data.entities.message.toDomainMessages
import pro.inmost.android.visario.data.entities.message.toMessagesData
import pro.inmost.android.visario.domain.entities.channel.Channel
import pro.inmost.android.visario.domain.entities.message.Message

fun ChannelData.toDomainChannel(messages: List<Message>) = Channel(
    url = channelArn,
    name = this.name,
    mode = this.mode,
    privacy = this.privacy,
    messages = messages,
    isMember = this.isMember,
    isModerator = this.isModerator,
    description = this.description ?: "",
    memberCount = this.membersCount
)

fun Channel.toChannelWithMessages(): ChannelWithMessages? {
    return kotlin.runCatching {
        ChannelWithMessages (
            channel = this.toDataChannel(),
            messages = this.messages.toMessagesData()
        )
    }.getOrNull()
}

fun Channel.toDataChannel(): ChannelData {
    return ChannelData(
        channelArn = this.url,
        name = this.name,
        mode = this.mode,
        privacy = this.privacy,
        metadata = "",
        isMember = this.isMember,
        isModerator = this.isModerator,
        description = this.description,
        membersCount = this.memberCount
    )
}

fun ChannelWithMessages.toDomainChannel() = Channel(
    url = channel.channelArn,
    name = channel.name,
    mode = channel.mode,
    privacy = channel.privacy,
    messages = this.messages.toDomainMessages(),
    isMember = channel.isMember,
    isModerator = channel.isModerator,
    description = channel.description ?: "",
    memberCount = channel.membersCount
)

fun List<ChannelWithMessages>.toDomainChannels() = map { it.toDomainChannel() }
fun List<Channel>.toChannelsWithMessages() = map { it.toChannelWithMessages() }
fun List<ChannelData>.toDomainChannelsWithoutMessages() = map { it.toDomainChannel(listOf()) }