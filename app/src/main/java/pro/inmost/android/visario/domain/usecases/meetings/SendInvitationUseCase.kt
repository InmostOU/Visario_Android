package pro.inmost.android.visario.domain.usecases.meetings


/**
 * Send invitation use case
 *
 */
interface SendInvitationUseCase {
    /**
     * Send a meeting invitation to the channel
     *
     * @param meetingId id of existing meeting
     * @param channelArn channel url from AWS Chime
     * @return [Result]
     */
    suspend fun send(meetingId: String, channelArn: String): Result<Unit>
}