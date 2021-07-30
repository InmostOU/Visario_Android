package pro.inmost.android.visario.data.network.chimeapi.auth.model

data class AuthResponse(
    val timestamp: String,
    val status: Int,
    val message: String,
    val error: String?,
    val path: String,
    val accessToken: String = "",
    val refreshToken: String = ""
)
