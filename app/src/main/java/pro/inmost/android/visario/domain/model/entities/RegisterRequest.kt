package pro.inmost.android.visario.domain.model.entities

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val matchingPassword: String,
    val firstName: String,
    val lastName: String,
    val birthday: Long
)
