package pro.inmost.android.visario.domain.usecases.meetings

interface InviteContactUseCase {
    suspend fun invite(userId: Long, meetingId: String): Result<Unit>
}