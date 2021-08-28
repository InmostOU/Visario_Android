package pro.inmost.android.visario.domain.usecases.meetings

import pro.inmost.android.visario.domain.repositories.MeetingsRepository

class JoinMeetingUseCaseImpl(private val repository: MeetingsRepository) : JoinMeetingUseCase {
    override suspend fun join(url: String): Result<String> {
        return repository.joinMeeting(url)
    }
}