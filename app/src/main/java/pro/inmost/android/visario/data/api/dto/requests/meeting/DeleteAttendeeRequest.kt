package pro.inmost.android.visario.data.api.dto.requests.meeting

data class DeleteAttendeeRequest(
    val meetingId: String,
    val userId: String
)
