package pro.inmost.android.visario.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.channel.Channel
import pro.inmost.android.visario.domain.entities.contact.Contact

interface ChannelsRepository {

    /**
     * Get observable list of channels the user is a member of
     *
     * @return [Flow] with list of channels
     */
    fun getChannels(): Flow<List<Channel>>

    /**
     * Get observable channel by channelArn
     *
     * @param channelArn channel url from AWS Chime
     * @return [Flow] with specified channel
     */
    fun getChannel(channelArn: String): Flow<Channel>

    /**
     * Update channel info
     *
     * @param channel [Channel]
     * @return successful or failure [Result]
     */
    suspend fun update(channel: Channel): Result<Unit>

    /**
     * Create new channel
     *
     * @param channel [Channel]
     * @return successful or failure [Result]
     */
    suspend fun create(channel: Channel): Result<Unit>

    /**
     * Leave
     *
     * @param channelArn channel url from AWS Chime
     * @return successful or failure [Result]
     */
    suspend fun leave(channelArn: String): Result<Unit>

    /**
     * Add member to the channel
     *
     * @param channelArn channel url from AWS Chime
     * @param userArn user url from AWS Chime
     * @return successful or failure [Result]
     */
    suspend fun addMember(channelArn: String, userArn: String): Result<Unit>

    /**
     * Update all channel data
     *
     */
    suspend fun refreshData()

    /**
     * Search channels by name
     *
     * @param name channel's name
     * @return list of [Channel] whose name contain the word in channel param
     */
    suspend fun search(name: String): List<Channel>

    /**
     * Get observable list of channel's members
     *
     * @return [Flow] with list of [Contact]
     */
    fun getChannelMembers(channelArn: String): Flow<List<Contact>>
}