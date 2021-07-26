package pro.inmost.android.visario.domain.model.entities

sealed class AuthResponse{
    data class OK(val tokens: Tokens? = null) : AuthResponse()

    data class Error(
        val timestamp: String,
        val status: Int,
        val error: String,
        val message: String,
        val path: String
    ) : AuthResponse()
}
