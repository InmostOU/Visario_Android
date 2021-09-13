package pro.inmost.android.visario.domain.usecases.meetings

interface InviteGroupUseCase {
    suspend fun invite(meetingId: String, channel: String): Result<Unit>
}