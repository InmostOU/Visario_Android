package pro.inmost.android.visario.data.network.chimeapi.channels.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import pro.inmost.android.visario.data.network.chimeapi.messages.Message

@Entity(tableName = "channels")
data class Channel(
    @PrimaryKey
    val channelArn: String,
    val name: String?,
    val mode: String?,
    val privacy: String?,
    val metadata: String?
){
    @Ignore
    var lastMessage: Message? = null

    fun getLastMessageContent() = lastMessage?.content ?: ""

    fun getLastMessageTime() = lastMessage?.getFormatDate() ?: ""
}