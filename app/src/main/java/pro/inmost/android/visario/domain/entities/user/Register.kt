package pro.inmost.android.visario.domain.entities.user


data class Register(
    val email: String,
    val username: String,
    val password: String,
    val matchingPassword: String,
    val firstName: String,
    val lastName: String
)
