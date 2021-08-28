package pro.inmost.android.visario.domain.usecases.meetings

import pro.inmost.android.visario.domain.repositories.MeetingsRepository

class CreateMeetingUseCaseImpl(private val repository: MeetingsRepository) : CreateMeetingUseCase {
    override suspend fun create(name: String): Result<Unit> {
        return repository.createMeeting(name)
    }
}