package pro.inmost.android.visario.data.api.services.meetings

import com.amazonaws.services.chime.sdk.meetings.session.CreateMeetingResponse
import pro.inmost.android.visario.data.api.dto.requests.meeting.CreateMeetingRequest
import pro.inmost.android.visario.data.api.dto.requests.meeting.JoinMeetingRequest
import pro.inmost.android.visario.data.api.dto.responses.meeting.JoinMeetingResponse

interface MeetingService {

    suspend fun create(request: CreateMeetingRequest): CreateMeetingResponse

    suspend fun join(request: JoinMeetingRequest): JoinMeetingResponse
}