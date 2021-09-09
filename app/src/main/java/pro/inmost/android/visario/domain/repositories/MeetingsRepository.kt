package pro.inmost.android.visario.domain.repositories

import com.amazonaws.services.chime.sdk.meetings.session.MeetingSessionConfiguration
import pro.inmost.android.visario.domain.entities.meeting.Attendee

interface MeetingsRepository {
    suspend fun createMeeting(): Result<MeetingSessionConfiguration>
    suspend fun joinMeeting(meetingId: String): Result<MeetingSessionConfiguration>
    suspend fun deleteMeeting(host: String): Result<Unit>

    suspend fun createAttendee(meetingId: String, userId: Long): Result<Unit>
    suspend fun deleteAttendee(userId: Long, meetingId: String): Result<Unit>
    suspend fun getAttendee(attendeeId: String): Result<Attendee>

    suspend fun invite(meetingId: String, channelArn: String): Result<Unit>
}