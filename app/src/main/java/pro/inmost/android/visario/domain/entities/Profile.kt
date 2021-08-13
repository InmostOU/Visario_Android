package pro.inmost.android.visario.domain.entities

data class Profile(
    val id: Long,
    val userUrl: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val birthdate: Long,
    val about: String,
    val image: String,
    val showEmailTo: String,
    val showPhoneNumberTo: String
)