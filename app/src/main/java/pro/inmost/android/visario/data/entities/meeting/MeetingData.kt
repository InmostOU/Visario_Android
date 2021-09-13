package pro.inmost.android.visario.data.entities.meeting

data class MeetingData(
    val meetingId: String,
    val externalMeetingId: String?,
    val mediaPlacement: MediaPlacement,
    val mediaRegion: String
)

data class MediaPlacement(
    val audioFallbackUrl: String,
    val audioHostUrl: String,
    val screenDataUrl: String,
    val screenSharingUrl: String,
    val screenViewingUrl: String,
    val signalingUrl: String,
    val turnControlUrl: String
)