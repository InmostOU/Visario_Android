package pro.inmost.android.visario.data.network.chimeapi.services

import pro.inmost.android.visario.data.network.chimeapi.messages.*
import pro.inmost.android.visario.data.network.chimeapi.session.SessionEndpoint
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MessagingService {
    @GET(Endpoints.MESSAGE_LIST)
    suspend fun getMessages(
        @Query("channelArn") channelArn: String
    ): MessagesResponse

    @POST(Endpoints.MESSAGE_SEND)
    suspend fun sendMessage(
        @Body body: MessageRequest
    ): SendMessageResponse

    @GET(Endpoints.MESSAGES_SESSION)
    suspend fun getMessagingSessionEndpoint(): SessionEndpoint
}