package pro.inmost.android.visario.data.repositories

import com.amazonaws.services.chime.sdk.meetings.session.CreateAttendeeResponse
import com.amazonaws.services.chime.sdk.meetings.session.CreateMeetingResponse
import com.amazonaws.services.chime.sdk.meetings.session.MeetingSessionConfiguration
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.dto.responses.meeting.toAWSAttendee
import pro.inmost.android.visario.data.api.dto.responses.meeting.toAWSMeeting
import pro.inmost.android.visario.data.database.dao.AttendeesDao
import pro.inmost.android.visario.data.database.dao.ContactsDao
import pro.inmost.android.visario.data.database.dao.ProfileDao
import pro.inmost.android.visario.data.entities.meeting.AttendeeData
import pro.inmost.android.visario.data.entities.meeting.toDomainAttendee
import pro.inmost.android.visario.domain.entities.meeting.Attendee
import pro.inmost.android.visario.domain.repositories.MeetingsRepository
import pro.inmost.android.visario.domain.repositories.MessagesRepository
import pro.inmost.android.visario.ui.utils.log

class MeetingsRepositoryImpl(
    private val api: ChimeApi,
    private val messagesRepository: MessagesRepository,
    private val attendeesDao: AttendeesDao,
    private val contactsDao: ContactsDao,
    private val profileDao: ProfileDao
    ) : MeetingsRepository {

    override suspend fun createMeeting(): Result<MeetingSessionConfiguration> {
        api.meetings.createMeeting().onSuccess { meetingResponse ->
            val configuration = MeetingSessionConfiguration(
                CreateMeetingResponse(meetingResponse.meeting.toAWSMeeting()),
                CreateAttendeeResponse(meetingResponse.attendee.toAWSAttendee())
            )
            saveAttendee(meetingResponse.attendee)
            return Result.success(configuration)
        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(Throwable("Create meeting: unknown error"))
    }

    private suspend fun saveAttendee(attendee: AttendeeData) {
        attendeesDao.insert(attendee)
    }

    override suspend fun createAttendee(meetingId: String, userId: Long): Result<Unit> {
        api.meetings.createAttendee(meetingId, userId).onSuccess {
            attendeesDao.insert(it)
            return Result.success(Unit)
        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(Throwable("Create attendee error"))
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
            saveAttendee(meetingResponse.attendee)
            return Result.success(configuration)
        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(Throwable("Create meeting: unknown error"))
    }

    override suspend fun getAttendee(attendeeId: String): Result<Attendee> {
        val attendee = attendeesDao.get(attendeeId)
        return if (attendee != null){
            val contact = contactsDao.get(attendee.userId)
            if (contact != null){
                Result.success(attendee.toDomainAttendee(contact))
            } else {
                val profile = profileDao.get()
                if (profile != null) {
                    Result.success(attendee.toDomainAttendee(profile))
                } else Result.failure(Throwable("Attendee not found"))
            }
        } else {
            log("Attendee not found")
            Result.failure(Throwable("Attendee not found"))
        }
    }

    override suspend fun invite(meetingId: String, channelArn: String): Result<Unit> {
        val message = api.meetings.getMeetingInvitationLink(meetingId)
        return messagesRepository.sendMessage(message, channelArn)
    }
}