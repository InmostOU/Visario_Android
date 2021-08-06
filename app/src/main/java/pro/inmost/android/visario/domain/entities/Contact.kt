package pro.inmost.android.visario.domain.entities

data class Contact(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val image: String,
    val about: String,
    var online: Boolean = false,
    var favorite: Boolean = false,
    var muted: Boolean = false,
    var inMyContacts: Boolean = false
){
    val fullName get() = "$firstName $lastName"
    val phoneShowing = true
    val emailShowing = true
}