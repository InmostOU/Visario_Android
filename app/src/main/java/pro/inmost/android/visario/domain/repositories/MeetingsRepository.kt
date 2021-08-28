package pro.inmost.android.visario.domain.repositories

interface MeetingsRepository {
    suspend fun createMeeting(name: String): Result<Unit>
    suspend fun joinMeeting(url: String): Result<String>
}