package pro.inmost.android.visario.domain.usecases.meetings.impl

import kotlinx.coroutines.flow.firstOrNull
import pro.inmost.android.visario.domain.repositories.MeetingsRepository
import pro.inmost.android.visario.domain.repositories.ProfileRepository
import pro.inmost.android.visario.domain.usecases.meetings.DeleteAttendeeUseCase

class DeleteAttendeeUseCaseImpl(
    private val meetingsRepository: MeetingsRepository,
    private val profileRepository: ProfileRepository
    ) :
    DeleteAttendeeUseCase {
    override suspend fun delete(userId: Long, meetingId: String): Result<Unit> {
        return meetingsRepository.deleteAttendee(userId, meetingId)
    }

    override suspend fun deleteMyself(meetingId: String): Result<Unit> {
        val profile = profileRepository.observeProfile().firstOrNull()
        return if (profile != null){
            meetingsRepository.deleteAttendee(profile.id, meetingId)
        } else Result.failure(Throwable("Leaving failed"))
    }
}