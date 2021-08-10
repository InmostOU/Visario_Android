package pro.inmost.android.visario.ui.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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
    var online: Boolean = false,
    var favorite: Boolean = false,
    var muted: Boolean = false,
    var inMyContacts: Boolean = false
) : Parcelable {

    val fullName get() = "$firstName $lastName"
    val phoneShowing get() = phoneNumber.isNotBlank()
    val emailShowing get() = email.isNotBlank()
}