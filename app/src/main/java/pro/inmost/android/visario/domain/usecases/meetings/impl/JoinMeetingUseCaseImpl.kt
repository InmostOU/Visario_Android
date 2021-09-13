package pro.inmost.android.visario.domain.usecases.meetings.impl

import com.amazonaws.services.chime.sdk.meetings.session.MeetingSessionConfiguration
import pro.inmost.android.visario.domain.repositories.MeetingsRepository
import pro.inmost.android.visario.domain.usecases.meetings.JoinMeetingUseCase

class JoinMeetingUseCaseImpl(private val repository: MeetingsRepository) : JoinMeetingUseCase {
    override suspend fun join(meetingId: String): Result<MeetingSessionConfiguration> {
        return repository.joinMeeting(meetingId)
    }
}