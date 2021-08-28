package pro.inmost.android.visario.data.repositories

import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.domain.repositories.MeetingsRepository

class MeetingsRepositoryImpl(private val api: ChimeApi) : MeetingsRepository {

    override suspend fun createMeeting(name: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun joinMeeting(url: String): Result<String> {
        return Result.success("")
    }
}