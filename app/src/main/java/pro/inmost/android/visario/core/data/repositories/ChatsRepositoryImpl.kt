package pro.inmost.android.visario.core.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import pro.inmost.android.visario.core.data.chimeapi.ChimeApi
import pro.inmost.android.visario.core.data.chimeapi.channels.model.Channel
import pro.inmost.android.visario.core.domain.entities.Chat
import pro.inmost.android.visario.core.domain.entities.ChatMode
import pro.inmost.android.visario.core.domain.entities.Privacy
import pro.inmost.android.visario.ui.boundaries.ChatsRepository

class ChatsRepositoryImpl(private val api: ChimeApi): ChatsRepository<Chat> {
    override suspend fun getChats(): List<Chat> {
        return api.channels.getAll().map { it.toChat()
        }
    }

    override fun observeChats(timeoutInMillis: Long): Flow<List<Chat>> {
        return flow {
            api.channels.observe(timeoutInMillis).collect { channels ->
                val chats = channels.map { it.toChat() }
                emit(chats)
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun Channel.toChat() =
        Chat(
            arn = channelArn,
            name = name ?: "",
            metadata = metadata,
            mode = ChatMode.valueOf(mode ?: "RESTRICTED"),
            privacy = Privacy.valueOf(privacy ?: "PUBLIC")
        )
}
