package pro.inmost.android.visario.domain.usecases.channels

import pro.inmost.android.visario.domain.entities.channel.Channel


/**
 * Create channel use case
 *
 * @constructor Create empty Create channel use case
 */
interface CreateChannelUseCase {
   /**
    * Create new channel
    *
    * @param channel [Channel]
    * @return [Result]
    */
   suspend fun create(channel: Channel): Result<Unit>
}