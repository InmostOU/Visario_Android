package pro.inmost.android.visario.domain.usecases.meetings

import pro.inmost.android.visario.domain.entities.meeting.Attendee

interface GetAttendeeUseCase {
    suspend fun get(attendeeId: String): Result<Attendee>
}