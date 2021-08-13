package pro.inmost.android.visario.ui.entities

import android.os.Parcelable
import android.text.format.DateFormat
import kotlinx.parcelize.Parcelize
import pro.inmost.android.visario.ui.utils.PROFILE_DATE_FORMAT

@Parcelize
data class ContactUI(
    val id: Int,
    val url: String,
    val username: String,
    var firstName: String,
    var lastName: String,
    val email: String,
    val phoneNumber: String,
    val image: String,
    val about: String,
    val birthdate: Long,
    var online: Boolean = false,
    var favorite: Boolean = false,
    var muted: Boolean = false,
    var inMyContacts: Boolean = false
) : Parcelable {

    val fullName get() = "$firstName $lastName"
    val phoneShowing get() = phoneNumber.isNotBlank()
    val emailShowing get() = email.isNotBlank()
    val birthdayShowing get() = birthdate != 0L

    val extraInfoShowing get() = phoneShowing || emailShowing || birthdayShowing

    val birthdateFormat: String
        get() = DateFormat.format(PROFILE_DATE_FORMAT, birthdate).toString()
}