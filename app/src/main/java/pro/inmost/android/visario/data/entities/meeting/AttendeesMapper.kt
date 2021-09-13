package pro.inmost.android.visario.data.entities.meeting

import pro.inmost.android.visario.data.api.dto.responses.meeting.AttendeeInfoResponse
import pro.inmost.android.visario.data.entities.contact.ContactData
import pro.inmost.android.visario.data.entities.profile.ProfileData
import pro.inmost.android.visario.domain.entities.meeting.Attendee

fun AttendeeInfoResponse.toDomainAttendee(isMe: Boolean = false) = Attendee(
    userId = id,
    image = image,
    name = "$firstName $lastName".trim(),
    isMe = isMe
)