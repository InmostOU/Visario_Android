package pro.inmost.android.visario.ui.entities.meeting

import pro.inmost.android.visario.domain.entities.meeting.Attendee

fun Attendee.toAttendeeUI(attendeeId: String = "") = AttendeeUI(
    attendeeId = attendeeId,
    userId = userId,
    name = name,
    image = image,
    isMe = isMe
)