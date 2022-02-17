package pro.inmost.android.visario.data.api.dto.requests.auth

import com.google.gson.annotations.SerializedName

data class GoogleLoginRequest(
    @SerializedName("idToken")
    val token: String
)
