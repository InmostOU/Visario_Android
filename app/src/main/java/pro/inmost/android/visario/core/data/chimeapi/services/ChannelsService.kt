package pro.inmost.android.visario.core.data.chimeapi.services

import pro.inmost.android.visario.core.data.chimeapi.channels.model.ChannelResponseBody
import retrofit2.http.GET

interface ChannelsService {
    @GET(URLs.CHANNEL_LIST)
    suspend fun getChannels(): ChannelResponseBody
}