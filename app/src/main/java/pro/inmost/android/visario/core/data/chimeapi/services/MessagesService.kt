package pro.inmost.android.visario.core.data.chimeapi.services

import pro.inmost.android.visario.core.data.chimeapi.messages.MessageRequest
import pro.inmost.android.visario.core.data.chimeapi.messages.MessagesResponse
import pro.inmost.android.visario.core.data.chimeapi.messages.SendMessageResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MessagesService {
    @GET(URLs.MESSAGE_LIST)
    suspend fun getMessages(
        @Query("channelArn") channelArn: String
    ): MessagesResponse

    @POST(URLs.MESSAGE_SEND)
    suspend fun sendMessage(
        @Body body: MessageRequest
    ): SendMessageResponse

    @GET(URLs.MESSAGES_SESSION)
    suspend fun getMessagingSessionUrl()
}