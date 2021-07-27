package pro.inmost.android.visario.core.data.chimeapi.auth.model

data class AuthResponse(
    val timestamp: String,
    val status: Int,
    val message: String,
    val error: String?,
    val path: String,
    val accessToken: String = "",
    val refreshToken: String = ""
)
