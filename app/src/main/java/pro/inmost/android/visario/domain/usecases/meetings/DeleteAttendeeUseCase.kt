package pro.inmost.android.visario.domain.usecases.meetings

interface DeleteAttendeeUseCase {
    suspend fun delete(userId: Long, meetingId: String): Result<Unit>
    suspend fun deleteMyself(meetingId: String): Result<Unit>
}