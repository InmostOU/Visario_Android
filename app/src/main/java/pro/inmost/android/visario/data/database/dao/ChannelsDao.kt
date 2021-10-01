package pro.inmost.android.visario.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.data.entities.channel.ChannelData
import pro.inmost.android.visario.data.entities.channel.ChannelWithMessages

@Dao
interface ChannelsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: ChannelData)
    @Update
    suspend fun update(vararg item: ChannelData)
    @Delete
    suspend fun delete(vararg item: ChannelData)

    @Query ("DELETE FROM channels WHERE channelArn =:channelArn")
    suspend fun deleteByArn(channelArn: String)

    @Query("SELECT * FROM channels")
    suspend fun getAll(): List<ChannelData>

    @Query("SELECT * FROM channels")
    fun getAllObservable(): Flow<List<ChannelData>>

    @Query("SELECT * FROM channels WHERE channelArn =:channelArn")
    suspend fun get(channelArn: String): ChannelData?

    @Query("SELECT * FROM channels WHERE channelArn =:channelArn")
    fun getChannelWithMessagesObservable(channelArn: String): Flow<ChannelWithMessages>

    @Transaction
    @Query("SELECT * FROM channels WHERE channelArn =:channelArn")
    suspend fun getChannelWithMessages(channelArn: String): ChannelWithMessages?

    @Transaction
    @Query("SELECT * FROM channels")
    fun getChannelsWithMessages(): Flow<List<ChannelWithMessages>>

    @Query("DELETE FROM channels")
    suspend fun deleteAll()

    @Transaction
    suspend fun fullUpdate(items: List<ChannelData>){
        deleteAll()
        insert(*items.toTypedArray())
    }
}