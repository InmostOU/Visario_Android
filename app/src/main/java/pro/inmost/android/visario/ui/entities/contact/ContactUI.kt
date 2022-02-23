package pro.inmost.android.visario.ui.entities.contact

import android.os.Parcelable
import android.text.format.DateFormat
import androidx.databinding.ObservableBoolean
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import pro.inmost.android.visario.ui.entities.BaseEntity
import pro.inmost.android.visario.utils.ONE_DAY_IN_MILLIS
import pro.inmost.android.visario.utils.ONE_WEEK_IN_MILLIS
import pro.inmost.android.visario.utils.PROFILE_DATE_FORMAT
import pro.inmost.android.visario.utils.extensions.toDateFormat
import pro.inmost.android.visario.utils.extensions.toDayFormat
import pro.inmost.android.visario.utils.extensions.toTimeFormat

@Parcelize
data class ContactUI(
    val id: Long,
    val userArn: String,
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
    var inMyContacts: Boolean = false,
    val lastSeen: Long
): BaseEntity, Parcelable {

    val fullName get() = "$firstName $lastName"
    val phoneShowing get() = phoneNumber.isNotBlank()
    val emailShowing get() = email.isNotBlank()
    val birthdayShowing get() = birthdate != 0L

    val extraInfoShowing get() = phoneShowing || emailShowing || birthdayShowing

    val birthdateFormat: String
        get() = DateFormat.format(PROFILE_DATE_FORMAT, birthdate).toString()

    val lastSeenFormat: String
        get() {
            val timeDiff = System.currentTimeMillis() - lastSeen
            return when {
                timeDiff > ONE_WEEK_IN_MILLIS -> lastSeen.toDateFormat()
                timeDiff > ONE_DAY_IN_MILLIS -> lastSeen.toDayFormat()
                else -> lastSeen.toTimeFormat()
            }
        }

    @IgnoredOnParcel
    val disabled = ObservableBoolean(false)
    @IgnoredOnParcel
    val invited = ObservableBoolean(false)

    override val baseId: String
        get() = userArn
}