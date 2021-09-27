package pro.inmost.android.visario.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.data.entities.message.MessageData

@Dao
interface MessagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: MessageData)
    @Update
    suspend fun update(vararg item: MessageData)
    @Delete
    suspend fun delete(vararg item: MessageData)

    @Query("DELETE FROM messages WHERE messageId =:messageId")
    suspend fun deleteById(messageId: String)

    @Query("SELECT * FROM messages")
    suspend fun getAll(): List<MessageData>

    @Query("SELECT * FROM messages WHERE messageId =:id")
    suspend fun get(id: String): MessageData?

    @Query("SELECT * FROM messages WHERE metadata =:meta")
    suspend fun getByMetadata(meta: String): MessageData?

    @Query("SELECT * FROM messages ORDER BY createdTimestamp DESC")
    fun getAllObservable(): Flow<List<MessageData>>

    @Query("SELECT * FROM messages WHERE channelArn =:channelArn ORDER BY createdTimestamp DESC")
    fun getChannelMessages(channelArn: String): Flow<List<MessageData>>

    @Query("UPDATE messages SET readByMe = 1 WHERE channelArn = :channelArn")
    suspend fun markAllMessagesAsRead(channelArn: String)

    @Query("SELECT EXISTS(SELECT * FROM messages WHERE messageId = :id)")
    suspend fun isRowExist(id: String) : Boolean

    @Query("DELETE FROM messages")
    suspend fun deleteAll()

    @Transaction
    suspend fun fullUpdate(items: List<MessageData>){
        deleteAll()
        insert(*items.toTypedArray())
    }

    @Transaction
    suspend fun updateChannelMessages(channelArn: String, items: List<MessageData>){
        insert(*items.toTypedArray())
    }

    @Query("UPDATE messages SET messageId =:newId, status = 'DELIVERED' WHERE messageId =:oldId")
    suspend fun updateMessageId(oldId: String, newId: String): Int

    @Transaction
    suspend fun upsert(message: MessageData){
        kotlin.runCatching {
            message.attachment?.messageId?.let { oldId ->
                updateMessageId(oldId, message.messageId)
            }
        }.onSuccess { result ->
            if (result == 0){
                insert(message)
            }
        }.onFailure {
            update(message)
        }
    }

    @Query("UPDATE messages SET status =:status WHERE messageId =:messageId")
    suspend fun updateSendingStatus(messageId: String, status: String)

    @Query("UPDATE messages SET readByMe =:read WHERE channelArn =:channelArn")
    suspend fun updateReadStatusForAll(channelArn: String, read: Boolean)

    @Query("UPDATE messages SET readByMe =:read WHERE messageId =:messageId")
    suspend fun updateReadStatus(messageId: String, read: Boolean)

    @Query("UPDATE messages SET content =:content, lastEditedTimestamp =:editedTimestamp WHERE messageId =:messageId")
    suspend fun updateContent(messageId: String, content: String, editedTimestamp: Long)
}