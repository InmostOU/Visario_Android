package pro.inmost.android.visario.data.database.dao

import androidx.room.*
import pro.inmost.android.visario.data.entities.ChannelData
import pro.inmost.android.visario.data.entities.ChannelWithMessages

@Dao
interface ChannelsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: ChannelData)
    @Update
    suspend fun update(vararg item: ChannelData)
    @Delete
    suspend fun delete(vararg item: ChannelData)

    @Query("SELECT * FROM channels")
    suspend fun getAll(): List<ChannelData>

    @Transaction
    @Query("SELECT * FROM channels WHERE channelArn =:channelArn")
    suspend fun getChannelWithMessages(channelArn: String): ChannelWithMessages?

    @Transaction
    @Query("SELECT * FROM channels")
    suspend fun getChannelsWithMessages(): List<ChannelWithMessages>
}