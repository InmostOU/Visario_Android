package pro.inmost.android.visario.domain.usecases.meetings


/**
 * Delete attendee from meeting use case
 *
 */
interface DeleteAttendeeUseCase {
    /**
     * Delete attendee from existing meeting
     *
     * @param userId id of user from meeting
     * @param meetingId id of existing meeting
     * @return [Result]
     */
    suspend fun delete(userId: Long, meetingId: String): Result<Unit>

    /**
     * Delete current user from existing meeting
     *
     * @param meetingId id of user from meeting
     * @return [Result]
     */
    suspend fun deleteCurrentUser(meetingId: String): Result<Unit>
}