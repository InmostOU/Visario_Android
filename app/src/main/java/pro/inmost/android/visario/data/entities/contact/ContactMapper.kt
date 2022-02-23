package pro.inmost.android.visario.data.entities.contact

import pro.inmost.android.visario.domain.entities.contact.Contact

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
    inMyContacts = this.inMyContacts,
    lastSeen = lastSeen
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

fun List<ContactData>.toDomainContacts() = map { it.toDomainContact() }
fun List<Contact>.toDataContacts() = map { it.toDataContact() }