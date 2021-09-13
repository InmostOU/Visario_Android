package pro.inmost.android.visario.data.api.services.meetings

import pro.inmost.android.visario.data.api.dto.requests.meeting.CreateAttendeeRequest
import pro.inmost.android.visario.data.api.dto.requests.meeting.DeleteAttendeeRequest
import pro.inmost.android.visario.data.api.dto.responses.meeting.AttendeeInfoResponse
import pro.inmost.android.visario.data.api.dto.responses.meeting.GetMeetingResponse
import pro.inmost.android.visario.data.api.services.Endpoints
import pro.inmost.android.visario.data.entities.meeting.AttendeeData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MeetingService {

    @POST(Endpoints.MEETING_CREATE)
    suspend fun createMeeting(): GetMeetingResponse

    @POST(Endpoints.MEETING_CREATE_ATTENDEE)
    suspend fun createAttendee(@Body request: CreateAttendeeRequest): AttendeeData

    @POST(Endpoints.MEETING_DELETE_ATTENDEE)
    suspend fun deleteAttendee(@Body request: DeleteAttendeeRequest)

    @GET(Endpoints.MEETING_GET)
    suspend fun getMeeting(@Query("meetingId") id: String): GetMeetingResponse

    @GET(Endpoints.MEETING_GET_ATTENDEE_INFO)
    suspend fun getAttendeeInfo(@Query("userId") userId: String): AttendeeInfoResponse

}