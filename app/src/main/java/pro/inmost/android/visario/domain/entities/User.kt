package pro.inmost.android.visario.domain.entities


data class User(
    val email: String,
    val username: String,
    val password: String,
    val matchingPassword: String,
    val firstName: String,
    val lastName: String,
    val birthdate: Long
)
