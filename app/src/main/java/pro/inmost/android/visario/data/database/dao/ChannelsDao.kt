package pro.inmost.android.visario.data.database.dao

import androidx.room.*
import pro.inmost.android.visario.data.network.chimeapi.channels.model.Channel

@Dao
interface ChannelsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: Channel)
    @Update
    suspend fun update(vararg item: Channel)
    @Delete
    suspend fun delete(vararg item: Channel)

    @Query("SELECT * FROM channels")
    suspend fun getAll(): List<Channel>
}