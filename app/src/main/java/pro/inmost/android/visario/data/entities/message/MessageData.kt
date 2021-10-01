package pro.inmost.android.visario.data.entities.message

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import pro.inmost.android.visario.data.api.dto.requests.messages.AttachmentData

@Entity(tableName = "messages")
data class MessageData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @SerializedName("messageId")
    val awsId: String,
    val content: String?,
    val createdTimestamp: Long,
    val senderArn: String,
    val channelArn: String,
    var senderName: String,
    var metadata: String = "",
    val type: String = MessageDataType.STANDARD,
    val lastEditedTimestamp: Long = 0L,
    val redacted: Boolean = false,
    var fromCurrentUser: Boolean = false,
    var readByMe: Boolean = false,
    var status: String? = MessageDataStatus.DELIVERED
): Comparable<MessageData> {

    override fun compareTo(other: MessageData): Int {
        return createdTimestamp.compareTo(other.createdTimestamp)
    }

    val attachment: AttachmentData?
        get() = kotlin.runCatching { Gson().fromJson(metadata, AttachmentData::class.java) }.getOrNull()
}