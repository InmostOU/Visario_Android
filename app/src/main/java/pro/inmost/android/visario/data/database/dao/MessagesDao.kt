package pro.inmost.android.visario.data.database.dao

import androidx.room.*
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

    @Query("SELECT * FROM messages WHERE channelArn =:channelArn")
    suspend fun getChannelMessages(channelArn: String): List<MessageData>
}