package pro.inmost.android.visario.data.repositories

import com.amazonaws.services.chime.sdk.meetings.session.CreateAttendeeResponse
import com.amazonaws.services.chime.sdk.meetings.session.CreateMeetingResponse
import com.amazonaws.services.chime.sdk.meetings.session.MeetingSessionConfiguration
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.dto.responses.meeting.toAWSAttendee
import pro.inmost.android.visario.data.api.dto.responses.meeting.toAWSMeeting
import pro.inmost.android.visario.data.database.dao.ProfileDao
import pro.inmost.android.visario.data.entities.meeting.toDomainAttendee
import pro.inmost.android.visario.domain.entities.meeting.Attendee
import pro.inmost.android.visario.domain.repositories.MeetingsRepository
import pro.inmost.android.visario.domain.repositories.MessagesRepository

class MeetingsRepositoryImpl(
    private val api: ChimeApi,
    private val profileDao: ProfileDao,
    private val messagesRepository: MessagesRepository
) : MeetingsRepository {

    override suspend fun createMeeting(): Result<MeetingSessionConfiguration> {
        api.meetings.createMeeting().onSuccess { meetingResponse ->
            val configuration = MeetingSessionConfiguration(
                CreateMeetingResponse(meetingResponse.meeting.toAWSMeeting()),
                CreateAttendeeResponse(meetingResponse.attendee.toAWSAttendee())
            )
            return Result.success(configuration)
        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(Throwable("Create meeting: unknown error"))
    }

    override suspend fun deleteMeeting(host: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAttendee(userId: Long, meetingId: String): Result<Unit> {
        return api.meetings.deleteAttendee(meetingId, userId)
    }

    override suspend fun joinMeeting(meetingId: String): Result<MeetingSessionConfiguration> {
        api.meetings.joinMeeting(meetingId).onSuccess { meetingResponse ->
            val configuration = MeetingSessionConfiguration(
                CreateMeetingResponse(meetingResponse.meeting.toAWSMeeting()),
                CreateAttendeeResponse(meetingResponse.attendee.toAWSAttendee())
            )
            return Result.success(configuration)
        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(Throwable("Create meeting: unknown error"))
    }

    override suspend fun getAttendee(userId: String): Result<Attendee> {
        api.meetings.getAttendeeInfo(userId).onSuccess { attendeeInfo ->
            val profile = profileDao.get()
            return Result.success(attendeeInfo.toDomainAttendee(profile?.id == attendeeInfo.id))
        }.onFailure { return Result.failure(it) }
        return Result.failure(Throwable("Get attendee info: unknown error"))
    }

    override suspend fun invite(meetingId: String, channelArn: String): Result<Unit> {
        val message = api.meetings.getMeetingInvitationLink(meetingId)
        return messagesRepository.sendMessage(message, channelArn)
    }
}