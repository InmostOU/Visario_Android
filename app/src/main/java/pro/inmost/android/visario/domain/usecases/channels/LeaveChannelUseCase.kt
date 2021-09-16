package pro.inmost.android.visario.domain.usecases.channels


/**
 * Leave channel use case
 *
 */
interface LeaveChannelUseCase {
    /**
     * Leave channel and delete it from user's list
     *
     * @param channelUrl channel url from AWS Chime
     * @return [Result]
     */
    suspend fun leave(channelUrl: String): Result<Unit>
}