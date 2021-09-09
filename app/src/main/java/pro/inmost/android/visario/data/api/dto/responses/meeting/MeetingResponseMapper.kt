package pro.inmost.android.visario.data.api.dto.responses.meeting

import com.amazonaws.services.chime.sdk.meetings.session.Attendee
import com.amazonaws.services.chime.sdk.meetings.session.Meeting
import pro.inmost.android.visario.data.entities.meeting.AttendeeData
import pro.inmost.android.visario.data.entities.meeting.MediaPlacement
import pro.inmost.android.visario.data.entities.meeting.MeetingData

fun MeetingData.toAWSMeeting() = Meeting(
    ExternalMeetingId = externalMeetingId,
    MediaPlacement = mediaPlacement.toAWSMediaPlacement(),
    MediaRegion = mediaRegion,
    MeetingId = meetingId
)

fun MediaPlacement.toAWSMediaPlacement() = com.amazonaws.services.chime.sdk.meetings.session.MediaPlacement(
    AudioFallbackUrl = audioFallbackUrl,
    AudioHostUrl = audioHostUrl,
    SignalingUrl = signalingUrl,
    TurnControlUrl = turnControlUrl
)

fun AttendeeData.toAWSAttendee() = Attendee(
    AttendeeId = attendeeId,
    ExternalUserId = userId,
    JoinToken = joinToken
)