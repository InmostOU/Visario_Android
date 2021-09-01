package pro.inmost.android.visario.data.repositories

import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.domain.repositories.MeetingsRepository

class MeetingsRepositoryImpl(private val api: ChimeApi) : MeetingsRepository {

    override suspend fun createMeeting(host: String): Result<Unit> {
        api.meetings.createMeeting(host)
        return Result.success(Unit)
    }

    override suspend fun createAttendee(meetingId: String, userId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getMeeting(host: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMeeting(host: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun joinMeeting(url: String): Result<String> {
        return Result.success("")
    }
}