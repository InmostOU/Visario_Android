package pro.inmost.android.visario.data.network

sealed class RegisterResponse{
    object OK : RegisterResponse()

    data class Error(
        val timestamp: String,
        val status: Int,
        val error: String,
        val message: String,
        val path: String
    ) : RegisterResponse()
}
