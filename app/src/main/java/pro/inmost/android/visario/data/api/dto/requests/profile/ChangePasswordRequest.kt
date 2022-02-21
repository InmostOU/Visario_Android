package pro.inmost.android.visario.data.api.dto.requests.profile

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String,
    val matchingPassword: String
)
