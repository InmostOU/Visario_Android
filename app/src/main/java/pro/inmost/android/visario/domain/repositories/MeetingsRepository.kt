package pro.inmost.android.visario.domain.repositories

interface MeetingsRepository {
    suspend fun createMeeting(host: String): Result<Unit>
    suspend fun createAttendee(meetingId: String, userId: String): Result<Unit>
    suspend fun getMeeting(host: String): Result<Unit>
    suspend fun deleteMeeting(host: String): Result<Unit>
    suspend fun joinMeeting(url: String): Result<String>
}