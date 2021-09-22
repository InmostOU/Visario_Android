package pro.inmost.android.visario.domain.usecases.channels

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.channel.Channel


/**
 * Fetch channels use case the user is a member of
 *
 */
interface FetchChannelsUseCase {
    /**
     * Get observable channels
     *
     * @return [Flow] with list of [Channel]
     */
    fun getChannels(): Flow<List<Channel>>

    /**
     * Get observable channel by channelArn
     *
     * @param channelArn channel url from AWS Chime
     * @return [Flow] with list of [Channel]
     */
    fun getChannel(channelArn: String): Flow<Channel>

    /**
     * Refresh all user's channels
     *
     */
    suspend fun refresh()

    /**
     * Search public channels by name
     *
     * @param channel name of channel
     * @return [List] of [Channel]
     */
    suspend fun search(channel: String): List<Channel>
}