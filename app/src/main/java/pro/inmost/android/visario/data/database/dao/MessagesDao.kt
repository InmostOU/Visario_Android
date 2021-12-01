package pro.inmost.android.visario.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.data.entities.message.MessageData
import pro.inmost.android.visario.data.entities.message.MessageDataStatus

@Dao
interface MessagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MessageData): Long
    @Update
    suspend fun update(vararg item: MessageData)
    @Delete
    suspend fun delete(vararg item: MessageData)

    @Query("DELETE FROM messages WHERE awsId = :awsId")
    suspend fun deleteByAwsId(awsId: String)

    @Query("SELECT * FROM messages")
    suspend fun getAll(): List<MessageData>

    @Query("SELECT * FROM messages WHERE awsId =:awsId")
    suspend fun getByAwsId(awsId: String): MessageData?

    @Query("SELECT * FROM messages WHERE id =:id")
    suspend fun getById(id: Long): MessageData?

    @Query("SELECT * FROM messages WHERE metadata =:meta")
    suspend fun getByMetadata(meta: String): MessageData?

    @Query("SELECT * FROM messages ORDER BY createdTimestamp DESC")
    fun getAllObservable(): Flow<List<MessageData>>

    @Query("SELECT * FROM messages WHERE channelArn =:channelArn ORDER BY createdTimestamp DESC")
    fun getChannelMessages(channelArn: String): Flow<List<MessageData>>

    @Query("UPDATE messages SET readByMe = 1 WHERE channelArn = :channelArn")
    suspend fun markAllMessagesAsRead(channelArn: String)

    @Query("SELECT EXISTS(SELECT * FROM messages WHERE awsId = :id)")
    suspend fun isRowExist(id: String) : Boolean

    @Query("DELETE FROM messages WHERE channelArn = :channelArn")
    suspend fun deleteAllMessagesInChannel(channelArn: String)

    @Transaction
    suspend fun updateChannelMessages(channelArn: String, items: List<MessageData>){
        items.forEach { message ->
            getByAwsId(message.awsId)?.let { message.readByMe = it.readByMe }
            message.status = MessageDataStatus.DELIVERED
        }
        deleteAllMessagesInChannel(channelArn)
        items.forEach { insert(it) }
    }

    @Query("UPDATE messages SET awsId =:newId WHERE awsId =:oldId")
    suspend fun updateAwsId(oldId: String, newId: String): Int

    @Transaction
    suspend fun upsert(message: MessageData){
        message.attachment?.messageId?.let { oldId ->
            updateAwsId(oldId, message.awsId)
        }
        val savedMessage = getByAwsId(message.awsId)
        if (savedMessage == null){
            if (message.fromCurrentUser) message.readByMe = true
            insert(message)
        } else {
            message.id = savedMessage.id
            message.readByMe = savedMessage.readByMe
            message.status = MessageDataStatus.DELIVERED
            update(message)
        }
    }

    @Query("UPDATE messages SET metadata =:metadata WHERE awsId = :awsId")
    suspend fun updateMetadata(metadata: String, awsId: String)

    @Query("UPDATE messages SET status =:status WHERE awsId = :awsId")
    suspend fun updateSendingStatus(awsId: String, status: String)

    @Query("UPDATE messages SET readByMe =:read WHERE channelArn =:channelArn")
    suspend fun updateReadStatusForAll(channelArn: String, read: Boolean)

    @Query("UPDATE messages SET readByMe =:read WHERE awsId = :awsId")
    suspend fun updateReadStatus(awsId: String, read: Boolean)

    @Query("UPDATE messages SET content =:content, lastEditedTimestamp =:editedTimestamp WHERE awsId = :awsId")
    suspend fun updateContent(awsId: String, content: String, editedTimestamp: Long)
}