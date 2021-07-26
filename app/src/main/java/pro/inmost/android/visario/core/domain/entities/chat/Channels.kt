package pro.inmost.android.visario.core.domain.entities.chat

import com.google.gson.annotations.SerializedName

data class Channels(
    @SerializedName("channelMemberships")
    val channels: List<Channel>
)