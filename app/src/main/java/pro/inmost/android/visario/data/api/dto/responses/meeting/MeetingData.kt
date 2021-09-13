package pro.inmost.android.visario.data.api.dto.responses.meeting

import com.google.gson.annotations.SerializedName
import pro.inmost.android.visario.data.entities.meeting.AttendeeData
import pro.inmost.android.visario.data.entities.meeting.MeetingData

data class GetMeetingResponse(
    @SerializedName("meetingObject")
    val meeting: MeetingData,
    @SerializedName("attendeeObject")
    val attendee: AttendeeData
)