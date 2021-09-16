package pro.inmost.android.visario.domain.usecases.meetings

import pro.inmost.android.visario.domain.entities.meeting.Attendee


/**
 * Get attendee use case
 *
 */
interface GetAttendeeUseCase {
    /**
     * Get attendee's basic info
     *
     * @param userId id of user from meeting
     * @param meetingId id of existing meeting
     * @return [Result] that encapsulates [Attendee] with basic user's info
     * or a failure with an arbitrary [Throwable] exception.
     */
    suspend fun get(userId: String, meetingId: String): Result<Attendee>
}