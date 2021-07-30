package pro.inmost.android.visario.data.network.chimeapi.messages

import pro.inmost.android.visario.data.network.chimeapi.services.MessagesService

class MessagesManager(private val service: MessagesService) {

    suspend fun getMessages(channelArn: String) = service.getMessages(channelArn)

    suspend fun getLastMessage(channelArn: String) = service.getMessages(channelArn).messages.lastOrNull()

    suspend fun sendMessage(request: MessageRequest) = service.sendMessage(request)

}