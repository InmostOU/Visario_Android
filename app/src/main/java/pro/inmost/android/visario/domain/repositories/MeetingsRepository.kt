package pro.inmost.android.visario.domain.repositories

import com.amazonaws.services.chime.sdk.meetings.session.MeetingSessionConfiguration
import pro.inmost.android.visario.domain.entities.meeting.Attendee

interface MeetingsRepository {
    /**
     * Create meeting
     *
     * @return [Result] that encapsulates [MeetingSessionConfiguration] for use to start meeting session
     * or a failure with an arbitrary [Throwable] exception.
     */
    suspend fun createMeeting(): Result<MeetingSessionConfiguration>

    /**
     * Join an existing meeting
     *
     * @param meetingId id of existing meeting
     * @return [Result] that encapsulates [MeetingSessionConfiguration] for use to start meeting session
     * or a failure with an arbitrary [Throwable] exception.
     */
    suspend fun joinMeeting(meetingId: String): Result<MeetingSessionConfiguration>

    /**
     * Delete meeting
     *
     * @param meetingId id of existing meeting
     * @return [Result]
     */
    suspend fun deleteMeeting(meetingId: String): Result<Unit>

    /**
     * Delete attendee from meeting
     *
     * @param userId id of contact from meeting
     * @param meetingId id of existing meeting
     * @return [Result]
     */
    suspend fun deleteAttendee(userId: Long, meetingId: String): Result<Unit>

    /**
     * Get attendee basic info
     *
     * @param userId id of contact from meeting
     * @return [Result] that encapsulates [Attendee] with basic user's info
     * or a failure with an arbitrary [Throwable] exception.
     */
    suspend fun getAttendee(userId: String, meetingId: String): Result<Attendee>

    /**
     * Send a meeting invitation to the channel
     *
     * @param meetingId id of existing meeting
     * @param channelArn channel url from AWS Chime
     * @return [Result]
     */
    suspend fun sendInvitation(meetingId: String, channelArn: String): Result<Unit>
}