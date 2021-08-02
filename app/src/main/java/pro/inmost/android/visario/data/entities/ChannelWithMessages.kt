package pro.inmost.android.visario.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ChannelWithMessages(
    @Embedded val channel: ChannelData,
    @Relation(
        parentColumn = "channelArn",
        entityColumn = "channelArn",
    )
    val messages: List<MessageData>
)