package pro.inmost.android.visario.domain.entities.meeting

data class Attendee(
    val attendeeId: String,
    val userId: Long,
    val name: String,
    val image: String,
    val isMe: Boolean = false
)