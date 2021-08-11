package pro.inmost.android.visario.ui.entities

import android.os.Parcelable
import android.text.format.DateFormat
import kotlinx.parcelize.Parcelize

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
    val showEmailTo: String,
    val showPhoneNumberTo: String
): Parcelable{

    val fullName: String
        get() = "$firstName $lastName"

    val birthdateFormat: String
        get() = DateFormat.format("dd MMMM yyyy", birthdate).toString()
}
