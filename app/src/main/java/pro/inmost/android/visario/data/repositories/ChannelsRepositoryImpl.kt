package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.database.dao.ChannelsDao
import pro.inmost.android.visario.data.entities.ChannelData
import pro.inmost.android.visario.data.utils.launchIO
import pro.inmost.android.visario.data.utils.toDataChannel
import pro.inmost.android.visario.data.utils.toDomainChannels
import pro.inmost.android.visario.domain.entities.Channel
import pro.inmost.android.visario.domain.repositories.ChannelsRepository
import pro.inmost.android.visario.domain.repositories.MessagesRepository

class ChannelsRepositoryImpl(
    private val api: ChimeApi,
    private val channelsDao: ChannelsDao,
    private val messagesRepository: MessagesRepository
) : ChannelsRepository {

    override fun getChannels(): Flow<List<Channel>> {
        val savedChannels = getSavedChannels().map { it.toDomainChannels() }
        launchIO { refreshData() }
        return savedChannels
    }

    override suspend fun refreshData(): Unit = withContext(IO) {
        api.channels.getChannels().onSuccess { data ->
            data.forEach { channel ->
                messagesRepository.refreshData(channel.channelArn)
            }
            updateDatabase(data)
        }
    }

    override fun getChannel(channelUrl: String) = flow {
        getChannels().collect { channels ->
            channels.find { it.url == channelUrl }?.let {
                emit(it)
            }
        }
    }

    override suspend fun update(channel: Channel): Result<Unit> {
        return api.channels.update(channel.toDataChannel()).onSuccess {
            channelsDao.update(channel.toDataChannel())
        }
    }

    override suspend fun create(channel: Channel): Result<Unit> {
        return api.channels.create(channel.toDataChannel()).onSuccess {
            launchIO { refreshData() }
        }
    }

    private fun getSavedChannels() = channelsDao.getChannelsWithMessages()

    private suspend fun updateDatabase(data: List<ChannelData>) {
        channelsDao.fullUpdate(data)
    }

    private suspend fun updateDatabase(data: ChannelData) {
        channelsDao.insert(data)
    }
}