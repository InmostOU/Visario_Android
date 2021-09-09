package pro.inmost.android.visario.data.entities.meeting

import pro.inmost.android.visario.data.entities.contact.ContactData
import pro.inmost.android.visario.data.entities.profile.ProfileData
import pro.inmost.android.visario.domain.entities.meeting.Attendee

fun AttendeeData.toDomainAttendee(contact: ContactData) = Attendee(
    attendeeId = this.attendeeId,
    userId = contact.id,
    image = contact.image,
    name = contact.firstName + " " + contact.lastName
)

fun AttendeeData.toDomainAttendee(profile: ProfileData) = Attendee(
    attendeeId = this.attendeeId,
    userId = profile.id,
    image = profile.image,
    name = profile.firstName + " " + profile.lastName,
    isMe = true
)