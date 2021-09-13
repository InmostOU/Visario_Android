package pro.inmost.android.visario.data.api.dto.requests.meeting

data class CreateAttendeeRequest(
    val meetingId: String,
    val userId: Long
)
