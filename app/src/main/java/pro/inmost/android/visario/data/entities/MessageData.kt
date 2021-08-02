package pro.inmost.android.visario.data.entities

import android.text.format.DateFormat
import androidx.room.Entity
import androidx.room.PrimaryKey
import pro.inmost.android.visario.ui.utils.DateParser

@Entity(tableName = "messages")
data class MessageData(
    @PrimaryKey
    val messageId: String,
    val content: String,
    val createdTimestamp: Long,
    val lastEditedTimestamp: String?,
    val metadata: String,
    val redacted: Boolean,
    val senderArn: String,
    val channelArn: String,
    val senderName: String,
    val type: String,
    val fromCurrentUser: Boolean = false
) {
    fun getFormatDate(): String {
        return DateFormat.format("hh:mm", createdTimestamp).toString()
    }
}