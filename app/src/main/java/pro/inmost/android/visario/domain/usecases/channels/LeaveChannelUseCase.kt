package pro.inmost.android.visario.domain.usecases.channels

interface LeaveChannelUseCase {
    suspend fun leave(channelUrl: String): Result<Unit>
}