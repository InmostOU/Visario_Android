package pro.inmost.android.visario.data.api.services.messages

import okhttp3.MultipartBody
import okhttp3.RequestBody
import pro.inmost.android.visario.data.api.dto.requests.messages.EditMessageRequest
import pro.inmost.android.visario.data.api.dto.requests.messages.SendMessageRequest
import pro.inmost.android.visario.data.api.dto.responses.base.BaseDataResponse
import pro.inmost.android.visario.data.api.dto.responses.base.BaseResponse
import pro.inmost.android.visario.data.api.services.Endpoints
import pro.inmost.android.visario.data.entities.message.MessageData
import retrofit2.http.*

/**
 * Messaging service to be implemented by retrofit
 *
 */
@JvmSuppressWildcards
interface MessagingService {
    @GET(Endpoints.MESSAGE_LIST)
    suspend fun getMessages(
        @Query("channelArn") channelArn: String
    ): BaseDataResponse<List<MessageData>>

    @POST(Endpoints.MESSAGE_SEND)
    suspend fun sendMessage(
        @Body body: SendMessageRequest
    ): BaseResponse

    @Multipart
    @POST(Endpoints.MESSAGE_SEND)
    suspend fun sendMessageWithAttachment(
        @Part file: MultipartBody.Part,
        @Part("message") message: RequestBody
    ): BaseResponse

    @POST(Endpoints.MESSAGE_EDIT)
    suspend fun editMessage(@Body request: EditMessageRequest): BaseResponse

    @DELETE(Endpoints.MESSAGE_DELETE)
    suspend fun deleteMessage(@Query("messageId") messageId: String): BaseResponse
}