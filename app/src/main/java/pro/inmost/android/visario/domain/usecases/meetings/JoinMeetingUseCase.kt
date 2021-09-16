package pro.inmost.android.visario.domain.usecases.meetings

import com.amazonaws.services.chime.sdk.meetings.session.MeetingSessionConfiguration


/**
 * Join meeting use case
 *
 */
interface JoinMeetingUseCase {
    /**
     * Join an existing meeting
     *
     * @param meetingId id of existing meeting
     * @return [Result] that encapsulates [MeetingSessionConfiguration] for use to start meeting session
     * or a failure with an arbitrary [Throwable] exception.
     */
    suspend fun join(meetingId: String): Result<MeetingSessionConfiguration>
}