package pro.inmost.android.visario.data.api.dto.responses.meeting

/**
 * Basic info about meeting attendee
 *
 */
data class AttendeeInfoResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val image: String
)
