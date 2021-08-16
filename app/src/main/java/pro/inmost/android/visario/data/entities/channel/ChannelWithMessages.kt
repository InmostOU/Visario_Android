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
    val messages: List<MessageData>
)