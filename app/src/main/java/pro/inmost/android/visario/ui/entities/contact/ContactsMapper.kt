package pro.inmost.android.visario.ui.entities.contact

import pro.inmost.android.visario.domain.entities.contact.Contact

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

fun List<Contact>.toUIContacts() = map { it.toUIContact() }
fun List<ContactUI>.toDomainContacts() = map { it.toDomainContact() }