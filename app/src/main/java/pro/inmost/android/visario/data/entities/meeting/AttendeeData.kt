package pro.inmost.android.visario.data.entities.meeting

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "attendees")
data class AttendeeData(
    @PrimaryKey
    @SerializedName("externalUserId")
    val userId: String,
    val attendeeId: String,
    val joinToken: String
)