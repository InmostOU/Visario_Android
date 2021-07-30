package pro.inmost.android.visario.data.network.chimeapi.channels.model

import com.google.gson.annotations.SerializedName

data class ChannelResponse(
    val status: Int,
    val message: String,
    @SerializedName("channelMemberships")
    val channels: List<Channel>
)