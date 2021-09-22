package pro.inmost.android.visario.data.api.services.channels

import pro.inmost.android.visario.data.api.dto.requests.channels.AddMemberToChannelRequest
import pro.inmost.android.visario.data.api.dto.requests.channels.CreateChannelRequest
import pro.inmost.android.visario.data.api.dto.responses.base.BaseDataResponse
import pro.inmost.android.visario.data.api.dto.responses.base.BaseResponse
import pro.inmost.android.visario.data.api.services.Endpoints
import pro.inmost.android.visario.data.entities.channel.ChannelData
import pro.inmost.android.visario.data.entities.channel.ChannelMember
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Channels service to be implemented by retrofit
 *
 */
interface ChannelsService {

    @GET(Endpoints.CHANNEL_LIST)
    suspend fun getChannels(): BaseDataResponse<List<ChannelData>>

    @POST(Endpoints.CHANNEL_CREATE)
    suspend fun create(@Body request: CreateChannelRequest): BaseResponse

    @GET(Endpoints.CHANNEL_LEAVE)
    suspend fun leave(@Query("channelArn") channelArn: String): BaseResponse

    @GET(Endpoints.CHANNEL_FIND_BY_NAME)
    suspend fun search(@Query("channelName") channelName: String): BaseDataResponse<List<ChannelData>>

    @POST(Endpoints.CHANNEL_ADD_MEMBER)
    suspend fun addMember(@Body request: AddMemberToChannelRequest): BaseResponse

    @GET(Endpoints.CHANNEL_GET_MEMBERS)
    suspend fun getMembers(@Query("channelArn") channelArn: String): BaseDataResponse<List<ChannelMember>>

    @GET(Endpoints.WS_CHANNELS)
    suspend fun getWebSocketLink(): BaseResponse
}