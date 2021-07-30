package pro.inmost.android.visario.data.network.chimeapi.auth.model

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val matchingPassword: String,
    val firstName: String,
    val lastName: String,
    val birthday: Long
)
