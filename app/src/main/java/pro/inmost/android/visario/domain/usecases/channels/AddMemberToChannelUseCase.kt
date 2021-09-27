package pro.inmost.android.visario.domain.usecases.channels

import pro.inmost.android.visario.domain.entities.channel.Channel


/**
 * Add member to channel use case
 *
 */
interface AddMemberToChannelUseCase {
    /**
     * Invite user to specified channel
     *
     * @param channelArn channel url from AWS Chime
     * @param userArn user url from AWS Chime
     * @return [Result]
     */
    suspend fun invite(channelArn: String, userArn: String): Result<Channel>
}