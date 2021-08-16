package pro.inmost.android.visario.data.api.services.messages

import pro.inmost.android.visario.data.api.dto.requests.MessageRequest
import pro.inmost.android.visario.data.api.dto.responses.StandardDataResponse
import pro.inmost.android.visario.data.api.services.Endpoints
import pro.inmost.android.visario.data.api.dto.responses.StandardResponse
import pro.inmost.android.visario.data.entities.message.MessageData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MessagingService {
    @GET(Endpoints.MESSAGE_LIST)
    suspend fun getMessages(
        @Query("channelArn") channelArn: String
    ): StandardDataResponse<MessageData>

    @POST(Endpoints.MESSAGE_SEND)
    suspend fun sendMessage(
        @Body body: MessageRequest
    ): StandardResponse
}