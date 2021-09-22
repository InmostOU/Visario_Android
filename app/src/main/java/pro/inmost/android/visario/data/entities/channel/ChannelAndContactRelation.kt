package pro.inmost.android.visario.data.entities.channel

import androidx.room.Entity
import androidx.room.ForeignKey
import pro.inmost.android.visario.data.entities.contact.ContactData

@Entity(
    primaryKeys = ["contactId", "channelId"],
    tableName = "channels_and_contacts",
    foreignKeys = [ForeignKey(
        entity = ChannelData::class,
        parentColumns = arrayOf("channelArn"),
        childColumns = arrayOf("channelId"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
        deferred = true
    ),
        ForeignKey(
            entity = ContactData::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("contactId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            deferred = true
        )]

)
data class ChannelAndContactRelation(
    val contactId: Long,
    val channelId: String
)

