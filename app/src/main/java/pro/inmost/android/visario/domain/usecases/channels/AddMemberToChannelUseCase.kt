package pro.inmost.android.visario.domain.usecases.channels

interface AddMemberToChannelUseCase {
    suspend fun invite(channelArn: String, userArn: String): Result<Unit>
}