package pro.inmost.android.visario.data.api.dto.responses.meeting

import pro.inmost.android.visario.data.entities.meeting.AttendeeData
import pro.inmost.android.visario.data.entities.meeting.MeetingData

data class GetMeetingResponse(
    val meeting: MeetingData,
    val attendee: AttendeeData
)