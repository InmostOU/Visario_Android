package pro.inmost.android.visario.ui.entities.contact

import pro.inmost.android.visario.domain.entities.contact.Contact

fun Contact.toUIContact() = ContactUI(
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
    inMyContacts = this.inMyContacts,
    birthdate = 0L,
    lastSeen = lastSeen
)

fun ContactUI.toDomainContact() = Contact(
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
    inMyContacts = this.inMyContacts,
    lastSeen = lastSeen
)

fun List<Contact>.toUIContacts() = map { it.toUIContact() }