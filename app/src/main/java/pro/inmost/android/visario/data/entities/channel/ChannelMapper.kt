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
    messages = messages
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
        metadata = ""
    )
}

fun ChannelWithMessages.toDomainChannel() = Channel(
    url = channel.channelArn,
    name = channel.name,
    mode = channel.mode,
    privacy = channel.privacy,
    messages = this.messages.toDomainMessages()
)

fun List<ChannelWithMessages>.toDomainChannels() = map { it.toDomainChannel() }
fun List<Channel>.toChannelsWithMessages() = map { it.toChannelWithMessages() }