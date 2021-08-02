package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.database.dao.ChannelsDao
import pro.inmost.android.visario.data.database.dao.MessagesDao
import pro.inmost.android.visario.data.network.utils.toChannelsWithMessages
import pro.inmost.android.visario.data.network.utils.toDomainChannel
import pro.inmost.android.visario.data.network.utils.toDomainChannels
import pro.inmost.android.visario.domain.repositories.ChannelsLocalRepository
import pro.inmost.android.visario.domain.entities.Channel

class ChannelsLocalRepositoryImpl(
    private val channelsDao: ChannelsDao,
    private val messagesDao: MessagesDao
) : ChannelsLocalRepository {
    override suspend fun getChannels() = withContext(IO) {
        channelsDao.getChannelsWithMessages().toDomainChannels()
    }

    override suspend fun getChannel(channelUrl: String) = withContext(IO) {
        channelsDao.getChannelWithMessages(channelUrl)?.toDomainChannel()
    }

    override suspend fun saveChannels(vararg channels: Channel) = withContext(IO) {
        channels.toList().toChannelsWithMessages().forEach { data ->
            channelsDao.insert(data.channel)
            messagesDao.insert(*data.messages.toTypedArray())
        }
    }
}