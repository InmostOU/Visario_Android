package pro.inmost.android.visario.core.data.chimeapi.messages

import pro.inmost.android.visario.core.data.chimeapi.services.MessagesService

class MessagesManager(private val service: MessagesService) {

    suspend fun getMessages(channelArn: String) = service.getMessages(channelArn)

    suspend fun sendMessage(request: MessageRequest) = service.sendMessage(request)

}