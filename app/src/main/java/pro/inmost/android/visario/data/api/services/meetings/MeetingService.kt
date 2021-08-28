package pro.inmost.android.visario.data.api.services.meetings

import pro.inmost.android.visario.data.api.dto.requests.meeting.CreateMeetingRequest
import pro.inmost.android.visario.data.api.dto.requests.meeting.JoinMeetingRequest
import pro.inmost.android.visario.data.api.dto.responses.meeting.MeetingResponse
import pro.inmost.android.visario.data.api.services.Endpoints
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MeetingService {

    @POST(Endpoints.MEETING_CREATE)
    suspend fun create(@Body request: CreateMeetingRequest): MeetingResponse

    @GET(Endpoints.MEETING_GET)
    suspend fun get(@Query("meetingHost") host: String): MeetingResponse

    suspend fun join(request: JoinMeetingRequest): MeetingResponse
}