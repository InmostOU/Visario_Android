package pro.inmost.android.visario.data.api.services.meetings

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.dto.requests.meeting.CreateAttendeeRequest
import pro.inmost.android.visario.data.api.dto.requests.meeting.DeleteAttendeeRequest
import pro.inmost.android.visario.data.api.dto.responses.meeting.GetMeetingResponse
import pro.inmost.android.visario.data.api.services.Endpoints
import pro.inmost.android.visario.data.entities.meeting.AttendeeData
import pro.inmost.android.visario.data.utils.logError
import pro.inmost.android.visario.data.utils.logInfo
import pro.inmost.android.visario.ui.utils.log

class MeetingManager(private val service: MeetingService) {

    suspend fun createMeeting(): Result<GetMeetingResponse> = withContext(IO) {
        kotlin.runCatching {
            val meetingResponse = service.createMeeting()
            log(meetingResponse.toString())
            Result.success(meetingResponse)
        }.getOrElse {
            logError("createMeeting error: ${it.message}")
            Result.failure(it)
        }
    }

    suspend fun createAttendee(meetingId: String, userId: Long): Result<AttendeeData> =
        withContext(IO) {
            kotlin.runCatching {
                val request = CreateAttendeeRequest(meetingId, userId)
                val response = service.createAttendee(request)
                logInfo("Create attendee response: $response")
                Result.success(response)
            }.getOrElse {
                logError("Create attendee error: ${it.message}")
                Result.failure(it)
            }
        }

    suspend fun deleteAttendee(meetingId: String, userId: Long): Result<Unit> = withContext(IO) {
        kotlin.runCatching {
            val request = DeleteAttendeeRequest(meetingId, userId.toString())
            val response = service.deleteAttendee(request)
            logInfo("Delete attendee response: $response")
            Result.success(Unit)
        }.getOrElse {
            logError("Delete attendee error: ${it.message}")
            Result.failure(it)
        }
    }

    fun getMeetingInvitationLink(meetingId: String) =
        "${Endpoints.SERVER_BASE_URL}${Endpoints.MEETING_GET}?meetingId=$meetingId"

    suspend fun joinMeeting(meetingId: String): Result<GetMeetingResponse> = withContext(IO) {
        kotlin.runCatching {
            val meetingResponse = service.getMeeting(meetingId)
            log(meetingResponse.toString())
            Result.success(meetingResponse)
        }.getOrElse {
            logError("Get meeting error: ${it.message}")
            Result.failure(it)
        }
    }
}