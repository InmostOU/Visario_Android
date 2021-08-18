package pro.inmost.android.visario.data.api.dto.requests

data class RegistrationRequest(
    val username: String,
    val email: String,
    val password: String,
    val matchingPassword: String,
    val firstName: String,
    val lastName: String
)
