package pro.inmost.android.visario.domain.usecases.meetings.impl

import com.amazonaws.services.chime.sdk.meetings.session.MeetingSessionConfiguration
import pro.inmost.android.visario.domain.repositories.MeetingsRepository
import pro.inmost.android.visario.domain.usecases.meetings.CreateMeetingUseCase

class CreateMeetingUseCaseImpl(private val repository: MeetingsRepository) : CreateMeetingUseCase {
    override suspend fun create(): Result<MeetingSessionConfiguration> {
        return repository.createMeeting()
    }
}