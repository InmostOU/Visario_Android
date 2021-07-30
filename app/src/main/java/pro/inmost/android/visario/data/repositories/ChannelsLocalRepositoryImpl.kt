package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.database.dao.ChannelsDao
import pro.inmost.android.visario.data.network.chimeapi.channels.model.Channel
import pro.inmost.android.visario.domain.boundaries.ChannelsLocalRepository

class ChannelsLocalRepositoryImpl (private val dao: ChannelsDao) : ChannelsLocalRepository{
    override suspend fun getChannels() = withContext(IO) {
        dao.getAll()
    }

    override suspend fun saveChannels(channels: List<Channel>) = withContext(IO) {
        dao.insert(*channels.toTypedArray())
    }
}