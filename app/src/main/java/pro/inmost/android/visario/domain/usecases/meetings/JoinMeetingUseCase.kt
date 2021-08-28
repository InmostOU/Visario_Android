package pro.inmost.android.visario.domain.usecases.meetings

interface JoinMeetingUseCase {
    suspend fun join(url: String): Result<String>
}