package pro.inmost.android.visario.domain.usecases.channels

import pro.inmost.android.visario.domain.entities.channel.Channel


/**
 * Update channel use case
 *
 */
interface UpdateChannelUseCase {
    /**
     * Update channel's info
     *
     * @param channel [Channel]
     * @return [Result]
     */
    suspend fun update(channel: Channel): Result<Unit>
}