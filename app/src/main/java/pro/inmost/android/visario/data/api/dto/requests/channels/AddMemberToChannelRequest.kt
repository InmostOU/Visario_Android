package pro.inmost.android.visario.data.api.dto.requests.channels

import com.google.gson.annotations.SerializedName

data class AddMemberToChannelRequest(
    val channelArn: String,
    @SerializedName("memberArn")
    val userArn: String
)
