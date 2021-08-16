package pro.inmost.android.visario.ui.entities.channel

import pro.inmost.android.visario.domain.entities.channel.Channel
import pro.inmost.android.visario.ui.entities.message.toDomainMessages
import pro.inmost.android.visario.ui.entities.message.toUIMessages

fun Channel.toUIChannel() = ChannelUI(
    url = this.url,
    name = this.name,
    mode = ChannelMode.valueOf(this.mode),
    privacy = ChannelPrivacy.valueOf(this.privacy),
    messages = this.messages.toUIMessages()
)

fun ChannelUI.toDomainChannel() = Channel(
    url = this.url,
    name = this.name,
    mode = this.mode.name,
    privacy = this.privacy.name,
    messages = this.messages.toDomainMessages()
)

fun List<Channel>.toUIChannels() = map { it.toUIChannel() }
fun List<ChannelUI>.toDomainChannels() = map { it.toDomainChannel() }