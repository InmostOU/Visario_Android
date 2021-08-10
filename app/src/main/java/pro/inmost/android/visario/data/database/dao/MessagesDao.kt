package pro.inmost.android.visario.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.data.entities.ChannelData
import pro.inmost.android.visario.data.entities.MessageData

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

    @Query("SELECT * FROM messages")
    fun getAllObservable(): Flow<List<MessageData>>

    @Query("SELECT * FROM messages WHERE channelArn =:channelArn")
    fun getChannelMessages(channelArn: String): Flow<List<MessageData>>

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
        delete(channelArn)
        insert(*items.toTypedArray())
    }
}