package pro.inmost.android.visario.data.entities.contact

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactData(
    @PrimaryKey
    val id: Long,
    val userArn: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val image: String,
    val about: String,
    var online: Boolean = false,
    var favorite: Boolean = false,
    var muted: Boolean = false,
    var inMyContacts: Boolean = false
) {
    val fullName: String
        get() = "$firstName $lastName".trim()
}