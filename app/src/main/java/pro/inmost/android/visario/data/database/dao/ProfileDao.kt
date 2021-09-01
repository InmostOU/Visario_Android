package pro.inmost.android.visario.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.data.entities.profile.ProfileData

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: ProfileData)
    @Update
    suspend fun update(vararg item: ProfileData)
    @Delete
    suspend fun delete(vararg item: ProfileData)

    @Query("SELECT * FROM profile LIMIT 1")
    suspend fun get(): ProfileData?

    @Query("SELECT * FROM profile LIMIT 1")
    fun getObservable(): Flow<ProfileData?>
}