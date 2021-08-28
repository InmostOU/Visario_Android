package pro.inmost.android.visario.domain.usecases.meetings

interface CreateMeetingUseCase {
    suspend fun create(name: String): Result<Unit>
}