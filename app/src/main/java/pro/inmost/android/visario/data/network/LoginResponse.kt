package pro.inmost.android.visario.data.network

sealed class LoginResponse{
    data class OK(
        val accessToken: String,
        val refreshToken: String
    ) : LoginResponse()

    data class Error(
        val timestamp: String,
        val status: Int,
        val error: String,
        val message: String,
        val path: String
    ) : LoginResponse()
}
