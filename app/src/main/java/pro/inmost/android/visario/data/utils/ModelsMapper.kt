package pro.inmost.android.visario.data.utils

import pro.inmost.android.visario.data.api.dto.requests.RegistrationRequest
import pro.inmost.android.visario.data.entities.*
import pro.inmost.android.visario.domain.entities.*

fun ChannelData.toDomainChannel(messages: List<Message>) = Channel(
    url = channelArn,
    name = this.name,
    mode = ChannelMode.valueOf(this.mode ?: "UNRESTRICTED"),
    privacy = Privacy.valueOf(this.privacy ?: "PUBLIC"),
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

fun Channel.toDataChannel(): ChannelData {
    return ChannelData(
        channelArn = this.url,
        name = this.name,
        mode = this.mode.name,
        privacy = this.privacy.name,
        metadata = ""
    )
}

fun ChannelWithMessages.toDomainChannel() = Channel(
    url = channel.channelArn,
    name = channel.name,
    mode = ChannelMode.valueOf(channel.mode ?: "UNRESTRICTED"),
    privacy = Privacy.valueOf(channel.privacy ?: "PUBLIC"),
    messages = this.messages.toDomainMessages()
)

fun User.toRegistrationRequest(): RegistrationRequest {
    return RegistrationRequest(
        username = this.username,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        birthday = this.birthdate,
        password = this.password,
        matchingPassword = this.password
    )
}

fun ContactData.toDomainContact() = Contact(
    id = this.id,
    url = this.userArn,
    firstName = this.firstName,
    lastName = this.lastName,
    username = this.username,
    email = this.email,
    phoneNumber = this.phoneNumber,
    image = this.image,
    about = this.about,
    online = this.online,
    favorite = this.favorite,
    muted = this.muted,
    inMyContacts = this.inMyContacts
)

fun Contact.toDataContact() = ContactData(
    id = this.id,
    userArn = this.url,
    firstName = this.firstName,
    lastName = this.lastName,
    username = this.username,
    email = this.email,
    phoneNumber = this.phoneNumber,
    image = this.image,
    about = this.about,
    online = this.online,
    favorite = this.favorite,
    muted = this.muted,
    inMyContacts = this.inMyContacts
)

fun ProfileData.toDomainProfile() = Profile(
    id = id,
    userUrl = userArn,
    firstName = firstName,
    lastName = lastName,
    username = username,
    about = about,
    birthdate = birthday,
    phoneNumber = phoneNumber,
    image = image,
    email = email,
    showEmailTo = showEmailTo,
    showPhoneNumberTo = showPhoneNumberTo
)

fun Profile.toDataProfile() = ProfileData(
    id = id,
    userArn = userUrl,
    firstName = firstName,
    lastName = lastName,
    username = username,
    about = about,
    birthday = birthdate,
    phoneNumber = phoneNumber,
    image = image,
    email = email,
    showEmailTo = showEmailTo,
    showPhoneNumberTo = showPhoneNumberTo
)

fun List<ContactData>.toDomainContacts() = map { it.toDomainContact() }
fun List<Contact>.toDataContacts() = map { it.toDataContact() }

fun List<MessageData>.toDomainMessages() = map { it.toDomainMessage()}
fun List<Message>.toMessagesData() = map { it.toDataMessage() }

fun List<ChannelWithMessages>.toDomainChannels() = map { it.toDomainChannel() }
fun List<Channel>.toChannelsWithMessages() = map { it.toChannelWithMessages() }

