package pro.inmost.android.visario.domain.usecases.meetings.impl

import pro.inmost.android.visario.domain.entities.meeting.Attendee
import pro.inmost.android.visario.domain.repositories.MeetingsRepository
import pro.inmost.android.visario.domain.usecases.meetings.GetAttendeeUseCase

class GetAttendeeUseCaseImpl(private val repository: MeetingsRepository) : GetAttendeeUseCase {
    override suspend fun get(userId: String): Result<Attendee> {
        return repository.getAttendee(userId)
    }
}