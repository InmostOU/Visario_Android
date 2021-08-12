package pro.inmost.android.visario.ui.entities

import pro.inmost.android.visario.domain.entities.*
import pro.inmost.android.visario.domain.entities.ChannelMode

fun Contact.toUIContact() = ContactUI(
    id = this.id,
    url = this.url,
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
    inMyContacts = this.inMyContacts,
    birthdate = 0L
)

fun ContactUI.toDomainContact() = Contact(
    id = this.id,
    url = this.url,
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

fun Channel.toUIChannel() = ChannelUI(
    url = this.url,
    name = this.name,
    mode = pro.inmost.android.visario.ui.entities.ChannelMode.valueOf(this.mode.name),
    privacy = ChannelPrivacy.valueOf(this.privacy.name),
    messages = this.messages.toUIMessages()
)

fun ChannelUI.toDomainChannel() = Channel(
    url = this.url,
    name = this.name,
    mode = ChannelMode.valueOf(this.mode.name),
    privacy = Privacy.valueOf(this.privacy.name),
    messages = this.messages.toDomainMessages()
)

fun MessageUI.toDomainMessage() = Message(
    messageId = this.messageId,
    text = this.text,
    channelUrl = this.channelUrl,
    senderUrl = this.senderUrl,
    senderName = this.senderName,
    createdTimestamp = this.createdTimestamp,
    lastEditedTimestamp = this.lastEditedTimestamp,
    type = this.type,
    redacted = this.redacted,
    fromCurrentUser = this.fromCurrentUser
)

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
    fromCurrentUser = this.fromCurrentUser
)

fun ProfileUI.toDomainProfile() = Profile(
    id = id,
    userUrl = userUrl,
    firstName = firstName,
    lastName = lastName,
    username = username,
    about = about,
    birthdate = birthdate,
    phoneNumber = phoneNumber,
    image = image,
    email = email,
    showEmailTo = showEmailTo,
    showPhoneNumberTo = showPhoneNumberTo
)

fun Profile.toUIProfile() = ProfileUI(
    id = id,
    userUrl = userUrl,
    firstName = firstName,
    lastName = lastName,
    username = username,
    about = about,
    birthdate = birthdate,
    phoneNumber = phoneNumber,
    image = image,
    email = email,
    showEmailTo = showEmailTo,
    showPhoneNumberTo = showPhoneNumberTo
)

fun List<Contact>.toUIContacts() = map { it.toUIContact() }
fun List<ContactUI>.toDomainContacts() = map { it.toDomainContact() }

fun List<Channel>.toUIChannels() = map { it.toUIChannel() }
fun List<ChannelUI>.toDomainChannels() = map { it.toDomainChannel() }

fun List<Message>.toUIMessages() = map { it.toUIMessage() }
fun List<MessageUI>.toDomainMessages() = map { it.toDomainMessage() }