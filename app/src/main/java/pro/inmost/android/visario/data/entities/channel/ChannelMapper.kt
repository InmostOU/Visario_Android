package pro.inmost.android.visario.data.entities.channel

import pro.inmost.android.visario.data.entities.message.toDomainMessages
import pro.inmost.android.visario.domain.entities.channel.Channel
import pro.inmost.android.visario.domain.entities.contact.Contact
import pro.inmost.android.visario.domain.entities.message.ReceivingMessage

fun ChannelData.toDomainChannel(messages: List<ReceivingMessage>) = Channel(
    url = channelArn,
    name = name,
    mode = mode,
    privacy = privacy,
    isMember = isMember,
    isAdmin = isAdmin,
    isModerator = isModerator,
    description = description ?: "",
    memberCount = membersCount,
    messages = messages
)

fun ChannelData.toDomainChannelWithoutMessages() = toDomainChannel(listOf())

fun Channel.toDataChannel(): ChannelData {
    return ChannelData(
        channelArn = this.url,
        name = this.name,
        mode = this.mode,
        privacy = this.privacy,
        metadata = "",
        isMember = this.isMember,
        isModerator = this.isModerator,
        isAdmin = this.isAdmin,
        description = this.description,
        membersCount = this.memberCount
    )
}

fun ChannelWithMessages.toDomainChannel() = Channel(
    url = channel.channelArn,
    name = channel.name,
    mode = channel.mode,
    privacy = channel.privacy,
    messages = messages.toDomainMessages(),
    isMember = channel.isMember,
    isAdmin = channel.isAdmin,
    isModerator = channel.isModerator,
    description = channel.description ?: "",
    memberCount = channel.membersCount
)

fun ChannelMember.toDomainContact() = Contact(
    id = userId,
    url = "",
    username = username,
    firstName = fullName.split(" ")[0],
    lastName = fullName.split(" ")[1],
    email = "",
    phoneNumber = "",
    about = "",
    image = "",
    online = true,
    lastSeen = System.currentTimeMillis()
)

fun List<ChannelWithMessages>.toDomainChannels() = mapNotNull { it.toDomainChannel() }
fun List<ChannelData>.toDomainChannelsWithoutMessages() = mapNotNull { it.toDomainChannel(listOf()) }
fun List<ChannelMember>.toDomainContacts() = mapNotNull { it.toDomainContact() }