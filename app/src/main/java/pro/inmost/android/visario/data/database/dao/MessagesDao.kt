package pro.inmost.android.visario.data.database.dao

import androidx.room.*
import pro.inmost.android.visario.data.network.chimeapi.channels.model.Channel
import pro.inmost.android.visario.data.network.chimeapi.messages.Message

@Dao
interface MessagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: Message)
    @Update
    suspend fun update(vararg item: Message)
    @Delete
    suspend fun delete(vararg item: Message)

    @Query("SELECT * FROM messages")
    suspend fun getAll(): List<Message>
}