package pro.inmost.android.visario.domain.usecases.meetings

import com.amazonaws.services.chime.sdk.meetings.session.MeetingSessionConfiguration

interface CreateMeetingUseCase {
    suspend fun create(): Result<MeetingSessionConfiguration>
}