package pro.inmost.android.visario.ui.entities

import android.os.Parcelable
import android.text.format.DateFormat
import kotlinx.parcelize.Parcelize
import pro.inmost.android.visario.ui.utils.PROFILE_DATE_FORMAT

@Parcelize
data class ProfileUI(
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
    val showEmailTo: Viewers,
    val showPhoneNumberTo: Viewers,
    val showBirthdateTo: Viewers
): Parcelable{

    val fullName: String
        get() = "$firstName $lastName"

    val birthdateFormat: String
        get() = DateFormat.format(PROFILE_DATE_FORMAT, birthdate).toString()
}
