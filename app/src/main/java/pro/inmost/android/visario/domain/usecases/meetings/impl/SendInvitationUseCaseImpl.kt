package pro.inmost.android.visario.domain.usecases.meetings.impl

import pro.inmost.android.visario.domain.repositories.MeetingsRepository
import pro.inmost.android.visario.domain.usecases.meetings.SendInvitationUseCase

class SendInvitationUseCaseImpl(private val repository: MeetingsRepository) : SendInvitationUseCase {
    override suspend fun send(meetingId: String, channelArn: String): Result<Unit> {
        return repository.sendInvitation(meetingId, channelArn)
    }
}