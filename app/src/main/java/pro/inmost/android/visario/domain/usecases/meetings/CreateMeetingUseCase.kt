package pro.inmost.android.visario.domain.usecases.meetings

import com.amazonaws.services.chime.sdk.meetings.session.MeetingSessionConfiguration


/**
 * Create the meeting use case
 *
 */
interface CreateMeetingUseCase {
    /**
     * Create new meeting
     *
     * @return [Result] that encapsulates [MeetingSessionConfiguration] for use to start meeting session
     * or a failure with an arbitrary [Throwable] exception.
     */
    suspend fun create(): Result<MeetingSessionConfiguration>
}