package pro.inmost.android.visario.data.api.dto.requests.auth

import com.google.gson.annotations.SerializedName

data class FacebookLoginRequest(
    @SerializedName("fbAccessToken")
    val token: String
)
