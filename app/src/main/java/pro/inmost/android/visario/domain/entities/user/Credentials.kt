package pro.inmost.android.visario.domain.entities.user

data class Credentials (
    val currentUser: String,
    val accessToken: String,
    val refreshToken: String
)
