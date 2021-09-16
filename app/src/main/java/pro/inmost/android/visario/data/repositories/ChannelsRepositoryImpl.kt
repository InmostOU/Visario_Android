package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.database.dao.ChannelsDao
import pro.inmost.android.visario.data.entities.channel.ChannelData
import pro.inmost.android.visario.data.entities.channel.toDataChannel
import pro.inmost.android.visario.data.entities.channel.toDomainChannels
import pro.inmost.android.visario.data.entities.channel.toDomainChannelsWithoutMessages
import pro.inmost.android.visario.data.utils.extensions.launchIO
import pro.inmost.android.visario.domain.entities.channel.Channel
import pro.inmost.android.visario.domain.repositories.ChannelsRepository
import pro.inmost.android.visario.domain.repositories.MessagesRepository
import pro.inmost.android.visario.ui.utils.log

class ChannelsRepositoryImpl(
    private val api: ChimeApi,
    private val channelsDao: ChannelsDao,
    private val messagesRepository: MessagesRepository
) : ChannelsRepository {

    override fun getChannels(): Flow<List<Channel>> {
        val savedChannels = getSavedChannels().map { it.toDomainChannels() }
        launchIO { refreshData() }
        return savedChannels.filter { it.isNotEmpty() }
    }

    override suspend fun refreshData(): Unit = withContext(IO) {
        api.channels.getChannels().onSuccess { data ->
            log("channels: $data")
            data.forEach { channel ->
                messagesRepository.refreshData(channel.channelArn)
            }
            updateDatabase(data)
        }
    }

    override suspend fun search(channel: String): List<Channel> {
        api.channels.search(channel).onSuccess {
            return it.toDomainChannelsWithoutMessages()
        }
        return emptyList()
    }

    override fun getChannel(channelArn: String) = flow {
        getChannels().collect { channels ->
            channels.find { it.url == channelArn }?.let {
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

    override suspend fun leave(channelArn: String): Result<Unit> {
        return api.channels.leave(channelArn).onSuccess {
            launchIO { refreshData() }
        }
    }

    override suspend fun addMember(channelArn: String, userArn: String): Result<Unit> {
        return api.channels.addMember(channelArn, userArn)
    }

    private fun getSavedChannels() = channelsDao.getChannelsWithMessages().map { data ->
        data.sortedDescending().onEach {
            it.messages = it.messages.sortedDescending()
        }
    }

    private suspend fun updateDatabase(data: List<ChannelData>) {
        channelsDao.fullUpdate(data)
    }

    private suspend fun updateDatabase(data: ChannelData) {
        channelsDao.insert(data)
    }
}