package pro.inmost.android.visario.data.database.dao

import androidx.room.*
import pro.inmost.android.visario.data.entities.meeting.AttendeeData

@Dao
interface AttendeesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: AttendeeData)
    @Update
    suspend fun update(vararg item: AttendeeData)
    @Delete
    suspend fun delete(vararg item: AttendeeData)

    @Query("SELECT * FROM attendees WHERE attendeeId =:attendeeId")
    suspend fun get(attendeeId: String): AttendeeData?
}