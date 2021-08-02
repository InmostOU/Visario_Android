package pro.inmost.android.visario.domain.usecases.messages

import pro.inmost.android.visario.domain.repositories.ChannelsNetworkRepository

class SendMessageUseCaseImpl(private val repository: ChannelsNetworkRepository) : SendMessageUseCase {
    override suspend fun send(channel: String, message: String): Result<String> {
        return repository.sendMessage(channel, message)
    }
}