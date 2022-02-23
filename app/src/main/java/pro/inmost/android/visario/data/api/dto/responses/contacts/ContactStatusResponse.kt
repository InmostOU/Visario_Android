package pro.inmost.android.visario.data.api.dto.responses.contacts

import com.google.gson.annotations.SerializedName

data class ContactStatusResponse(
    val id: Long,
    @SerializedName("user_arn")
    val userArn: String,
    val status: String,
    @SerializedName("last_seen")
    val lastSeen: Long
)
