package pro.inmost.android.visario.data.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import pro.inmost.android.visario.data.entities.channel.ChannelData

interface ChannelAndContactRelationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: ChannelData)
    @Update
    suspend fun update(vararg item: ChannelData)
    @Delete
    suspend fun delete(vararg item: ChannelData)
}