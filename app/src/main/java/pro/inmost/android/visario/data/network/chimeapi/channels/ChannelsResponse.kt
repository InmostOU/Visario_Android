package pro.inmost.android.visario.data.network.chimeapi.channels

import com.google.gson.annotations.SerializedName
import pro.inmost.android.visario.data.entities.ChannelData

data class ChannelsResponse(
    val status: Int,
    val message: String,
    @SerializedName("channelMemberships")
    val channels: List<ChannelData>
)