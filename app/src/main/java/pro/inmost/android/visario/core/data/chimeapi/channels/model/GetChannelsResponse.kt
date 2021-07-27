package pro.inmost.android.visario.core.data.chimeapi.channels.model

import com.google.gson.annotations.SerializedName

data class GetChannelsResponse(
    val status: Int,
    val message: String,
    @SerializedName("channelMemberships")
    val channels: List<Channel>
)