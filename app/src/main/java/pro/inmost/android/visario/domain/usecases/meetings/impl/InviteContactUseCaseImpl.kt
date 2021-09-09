package pro.inmost.android.visario.domain.usecases.meetings.impl

import pro.inmost.android.visario.domain.repositories.MeetingsRepository
import pro.inmost.android.visario.domain.usecases.meetings.InviteContactUseCase

class InviteContactUseCaseImpl(private val repository: MeetingsRepository) : InviteContactUseCase {
    override suspend fun invite(userId: Long, meetingId: String): Result<Unit> {
        return repository.createAttendee(meetingId, userId)
    }
}