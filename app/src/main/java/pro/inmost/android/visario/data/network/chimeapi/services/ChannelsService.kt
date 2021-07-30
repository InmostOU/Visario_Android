package pro.inmost.android.visario.data.network.chimeapi.services

import pro.inmost.android.visario.data.network.chimeapi.channels.model.ChannelResponse
import retrofit2.http.GET

interface ChannelsService {
    @GET(URLs.CHANNEL_LIST)
    suspend fun getChannels(): ChannelResponse
}