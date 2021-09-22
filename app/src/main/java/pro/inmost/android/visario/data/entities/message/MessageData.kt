package pro.inmost.android.visario.data.entities.message

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageData(
    @PrimaryKey
    val messageId: String,
    val content: String?,
    val createdTimestamp: Long,
    val senderArn: String,
    val channelArn: String,
    var senderName: String,
    val type: String = MessageDataType.STANDARD,
    var metadata: String = "",
    val lastEditedTimestamp: Long = 0L,
    val redacted: Boolean = false,
    val fromCurrentUser: Boolean = false,
    var readByMe: Boolean = false,
    var status: String? = MessageDataStatus.DELIVERED
): Comparable<MessageData> {

    override fun compareTo(other: MessageData): Int {
        return createdTimestamp.compareTo(other.createdTimestamp)
    }
}