package pro.inmost.android.visario.domain.usecases.meetings

import com.amazonaws.services.chime.sdk.meetings.session.MeetingSessionConfiguration

interface JoinMeetingUseCase {
    suspend fun join(meetingId: String): Result<MeetingSessionConfiguration>
}