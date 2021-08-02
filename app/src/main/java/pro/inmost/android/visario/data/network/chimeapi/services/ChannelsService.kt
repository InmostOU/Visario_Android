package pro.inmost.android.visario.data.network.chimeapi.services

import pro.inmost.android.visario.data.network.chimeapi.channels.ChannelsResponse
import retrofit2.http.GET

interface ChannelsService {
    @GET(Endpoints.CHANNEL_LIST)
    suspend fun getChannels(): ChannelsResponse
}