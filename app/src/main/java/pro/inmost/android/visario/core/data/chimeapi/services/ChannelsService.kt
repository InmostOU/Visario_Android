package pro.inmost.android.visario.core.data.chimeapi.services

import pro.inmost.android.visario.core.data.chimeapi.channels.model.GetChannelsResponse
import retrofit2.http.GET

interface ChannelsService {
    @GET("channels/getChannelsMemberships")
    suspend fun getChannels(): GetChannelsResponse
}