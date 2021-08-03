package pro.inmost.android.visario.data.network.chimeapi.messages

import pro.inmost.android.visario.data.network.chimeapi.services.MessagingService
import pro.inmost.android.visario.data.network.utils.urlEncode

class MessagesManager(
    private val service: MessagingService
) {

    suspend fun getMessages(channelArn: String) = service.getMessages(channelArn.urlEncode())

    suspend fun sendMessage(request: MessageRequest) = service.sendMessage(request)

    suspend fun getSessionEndpoint() = service.getMessagingSessionEndpoint()

}