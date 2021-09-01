package pro.inmost.android.visario.data.entities.channel

import androidx.room.Embedded
import androidx.room.Relation
import pro.inmost.android.visario.data.entities.message.MessageData

data class ChannelWithMessages(
    @Embedded val channel: ChannelData,
    @Relation(
        parentColumn = "channelArn",
        entityColumn = "channelArn",
    )
    var messages: List<MessageData>
) : Comparable<ChannelWithMessages>{

    override fun compareTo(other: ChannelWithMessages): Int {
        val lastMessageTime = kotlin.runCatching { messages.sortedDescending()[0].createdTimestamp }.getOrElse { 0 }
        val lastMessageTime2 = kotlin.runCatching { other.messages.sortedDescending()[0].createdTimestamp }.getOrElse { 0 }
        return lastMessageTime.compareTo(lastMessageTime2)
    }

}