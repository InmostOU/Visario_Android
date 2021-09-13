package pro.inmost.android.visario.domain.usecases.meetings.impl

import pro.inmost.android.visario.domain.repositories.MeetingsRepository
import pro.inmost.android.visario.domain.usecases.meetings.InviteGroupUseCase

class InviteGroupUseCaseImpl(private val repository: MeetingsRepository) : InviteGroupUseCase {
    override suspend fun invite(meetingId: String, channel: String): Result<Unit> {
        return repository.invite(meetingId, channel)
    }
}