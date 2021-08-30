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

    @Query("SELECT * FROM messages")
    suspend fun getAll(): List<MessageData>

    @Query("SELECT * FROM messages ORDER BY createdTimestamp DESC")
    fun getAllObservable(): Flow<List<MessageData>>

    @Query("SELECT * FROM messages WHERE channelArn =:channelArn ORDER BY createdTimestamp DESC")
    fun getChannelMessages(channelArn: String): Flow<List<MessageData>>

    @Query("UPDATE messages SET readByMe = 1 WHERE channelArn = :channelArn")
    suspend fun markAllMessagesAsRead(channelArn: String)

    @Query("SELECT EXISTS(SELECT * FROM messages WHERE messageId = :id)")
    suspend fun isRowIsExist(id : String) : Boolean

    @Query("DELETE FROM messages")
    suspend fun deleteAll()

    @Query("DELETE FROM messages WHERE channelArn =:channelArn")
    suspend fun delete(channelArn: String)

    @Transaction
    suspend fun fullUpdate(items: List<MessageData>){
        deleteAll()
        insert(*items.toTypedArray())
    }

    @Transaction
    suspend fun updateChannelMessages(channelArn: String, items: List<MessageData>){
        insert(*items.toTypedArray())
    }

    @Query("UPDATE messages SET messageId =:messageId, status = 'DELIVERED' WHERE content =:content and senderArn =:senderArn and channelArn =:channelArn and status = 'SENDING'")
    suspend fun updateMessageId(messageId: String, content: String, senderArn: String, channelArn: String): Int

    @Transaction
    suspend fun upsert(message: MessageData){
        val result = updateMessageId(message.messageId, message.content, message.senderArn, message.channelArn)
        if (result == 0){
            insert(message)
        }
    }
}