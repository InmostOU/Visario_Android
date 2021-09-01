package pro.inmost.android.visario.data.entities.profile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileData (
    @PrimaryKey
    val id: Long,
    val userArn: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val birthday: Long,
    val about: String,
    val image: String,
    val showEmailTo: String,
    val showPhoneNumberTo: String
)
