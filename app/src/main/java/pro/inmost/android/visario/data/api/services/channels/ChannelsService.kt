package pro.inmost.android.visario.data.api.services.channels

import pro.inmost.android.visario.data.api.dto.responses.StandardDataResponse
import pro.inmost.android.visario.data.api.services.Endpoints
import pro.inmost.android.visario.data.entities.ChannelData
import retrofit2.http.GET

interface ChannelsService {
    @GET(Endpoints.CHANNEL_LIST)
    suspend fun getChannels(): StandardDataResponse<ChannelData>
}