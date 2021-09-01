package pro.inmost.android.visario.data.api.dto.responses.meeting

data class MeetingResponse(
    val externalMeetingId: String,
    val mediaPlacement: MediaPlacement,
    val mediaRegion: String,
    val meetingId: String
)

data class MediaPlacement (
    val audioFallbackUrl: String,
    val audioHostUrl: String,
    val screenDataUrl: String,
    val screenSharingUrl: String,
    val screenViewingUrl: String,
    val signalingUrl: String,
    val turnControlUrl: String
)
