package pro.inmost.android.visario.data.api.services.channels

import pro.inmost.android.visario.data.api.dto.requests.CreateChannelRequest
import pro.inmost.android.visario.data.api.dto.responses.StandardDataResponse
import pro.inmost.android.visario.data.api.dto.responses.StandardResponse
import pro.inmost.android.visario.data.api.services.Endpoints
import pro.inmost.android.visario.data.entities.channel.ChannelData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChannelsService {
    @GET(Endpoints.CHANNEL_LIST)
    suspend fun getChannels(): StandardDataResponse<ChannelData>

    @POST(Endpoints.CHANNEL_CREATE)
    suspend fun create(@Body request: CreateChannelRequest): StandardResponse

    @GET(Endpoints.CHANNEL_LEAVE)
    suspend fun leave(@Query("channelArn") channelArn: String): StandardResponse

    @GET(Endpoints.CHANNEL_FIND_BY_NAME)
    suspend fun search(@Query("channelName") channelName: String): StandardDataResponse<ChannelData>
}