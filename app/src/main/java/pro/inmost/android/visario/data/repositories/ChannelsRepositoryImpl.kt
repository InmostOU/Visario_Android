package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.database.dao.ChannelsDao
import pro.inmost.android.visario.data.database.dao.ProfileDao
import pro.inmost.android.visario.data.entities.channel.*
import pro.inmost.android.visario.data.entities.contact.toDomainContacts
import pro.inmost.android.visario.domain.entities.channel.Channel
import pro.inmost.android.visario.domain.repositories.ChannelsRepository
import pro.inmost.android.visario.domain.repositories.MessagesRepository
import pro.inmost.android.visario.utils.extensions.launchIO

class ChannelsRepositoryImpl(
    private val api: ChimeApi,
    private val channelsDao: ChannelsDao,
    private val profileDao: ProfileDao,
    private val messagesRepository: MessagesRepository
) : ChannelsRepository {

    override fun getChannels(): Flow<List<Channel>> {
        launchIO { refreshData() }
        return getSavedChannels().map { it.toDomainChannels() }
            .filter { it.isNotEmpty() }
    }

    override suspend fun refreshData(): Unit = withContext(IO) {
        api.channels.getChannels().onSuccess { channels ->
            updateDatabase(channels)
            channels.forEach { channel ->
                messagesRepository.refreshData(channel.channelArn)
            }
        }
    }

    override suspend fun search(name: String): List<Channel> {
        api.channels.search(name).onSuccess {
            return it.toDomainChannelsWithoutMessages()
        }
        return emptyList()
    }

    override fun getChannelMembers(channelArn: String) = flow {
        api.channels.getMembers(channelArn).onSuccess {
            emit(it.toDomainContacts())
        }
    }

    override suspend fun getChannel(channelArn: String): Result<Channel> {
        val savedChannel = channelsDao.get(channelArn)?.toDomainChannelWithoutMessages()
        return if (savedChannel == null) {
            api.channels.getChannel(channelArn).map { it.toDomainChannelWithoutMessages() }
        } else {
            Result.success(savedChannel)
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
            channelsDao.deleteByArn(channelArn)
        }
    }

    override suspend fun addMember(channelArn: String, userArn: String): Result<Channel> {
        return api.channels.addMember(channelArn, userArn).mapCatching {
            api.channels.getChannel(channelArn).onSuccess { channel ->
                if (userArn == profileDao.get()?.userArn) {
                    channel.isMember = true
                    channelsDao.insert(channel)
                }
            }
            getChannel(channelArn).getOrThrow()
        }
    }

    private fun getSavedChannels() = channelsDao.getChannelsWithMessages().map { data ->
        data.sortedDescending().onEach {
            it.messages = it.messages.sortedDescending()
        }
    }

    private suspend fun updateDatabase(data: List<ChannelData>) {
        channelsDao.fullUpdate(data)
    }
}