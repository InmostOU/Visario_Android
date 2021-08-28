package pro.inmost.android.visario.data.api.services.meetings

import com.amazonaws.services.chime.sdk.meetings.session.CreateMeetingResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.dto.requests.meeting.CreateMeetingRequest
import pro.inmost.android.visario.data.api.dto.requests.meeting.JoinMeetingRequest
import pro.inmost.android.visario.data.api.dto.responses.meeting.JoinMeetingResponse
import pro.inmost.android.visario.data.api.utils.logError

class MeetingManager(private val service: MeetingService) {

    suspend fun createMeeting(name: String): Result<CreateMeetingResponse> = withContext(IO){
        val request = CreateMeetingRequest(name)
        kotlin.runCatching {
            val response = service.create(request)
            Result.success(response)
        }.getOrElse {
            logError("createMeeting error: ${it.message}")
            Result.failure(it)
        }
    }

    suspend fun joinMeeting(url: String): Result<JoinMeetingResponse> = withContext(IO){
        val request = JoinMeetingRequest(url)
        kotlin.runCatching {
            val response = service.join(request)
            Result.success(response)
        }.getOrElse {
            logError("joinMeeting error: ${it.message}")
            Result.failure(it)
        }
    }
}