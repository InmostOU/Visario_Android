package pro.inmost.android.visario.data.entities.channel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class ChannelData(
    @PrimaryKey
    val channelArn: String,
    val name: String,
    val mode: String,
    val privacy: String,
    val metadata: String?
)