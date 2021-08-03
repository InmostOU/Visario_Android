package pro.inmost.android.visario.domain.entities

data class Credentials (
    val currentUser: String,
    val accessToken: String,
    val refreshToken: String
)
