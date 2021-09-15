package pro.inmost.android.visario.data.api.services.messages

import pro.inmost.android.visario.data.api.dto.requests.messages.EditMessageRequest
import pro.inmost.android.visario.data.api.dto.requests.messages.SendMessageRequest
import pro.inmost.android.visario.data.api.dto.responses.base.BaseDataResponse
import pro.inmost.android.visario.data.api.dto.responses.base.BaseResponse
import pro.inmost.android.visario.data.api.services.Endpoints
import pro.inmost.android.visario.data.entities.message.MessageData
import retrofit2.http.*

interface MessagingService {
    @GET(Endpoints.MESSAGE_LIST)
    suspend fun getMessages(
        @Query("channelArn") channelArn: String
    ): BaseDataResponse<List<MessageData>>

    @POST(Endpoints.MESSAGE_SEND)
    suspend fun sendMessage(
        @Body body: SendMessageRequest
    ): BaseResponse

    @POST(Endpoints.MESSAGE_EDIT)
    suspend fun editMessage(@Body request: EditMessageRequest): BaseResponse

    @DELETE(Endpoints.MESSAGE_DELETE)
    suspend fun deleteMessage(@Query("messageId") messageId: String): BaseResponse
}