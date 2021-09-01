package pro.inmost.android.visario.data.api.dto.requests.profile

data class UpdateProfileRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val birthday: Long,
    val about: String,
    val showEmailTo: String,
    val showPhoneNumberTo: String
)
