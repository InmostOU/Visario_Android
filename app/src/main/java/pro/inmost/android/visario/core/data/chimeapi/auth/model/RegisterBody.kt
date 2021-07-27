package pro.inmost.android.visario.core.data.chimeapi.auth.model

data class RegisterBody(
    val username: String,
    val email: String,
    val password: String,
    val matchingPassword: String,
    val firstName: String,
    val lastName: String,
    val birthday: Long
)
