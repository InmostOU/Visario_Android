package pro.inmost.android.visario.domain.entities.contact

data class Contact(
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
){
    val fullName: String get() = "$firstName $lastName"
}