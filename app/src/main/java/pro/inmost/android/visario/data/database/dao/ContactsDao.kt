package pro.inmost.android.visario.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.data.entities.contact.ContactData

@Dao
interface ContactsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: ContactData)
    @Update
    suspend fun update(vararg item: ContactData)
    @Delete
    suspend fun deleteByUsername(vararg item: ContactData)

    @Query("DELETE FROM contacts WHERE username = :username")
    suspend fun deleteByUsername(username: String)

    @Query("DELETE FROM contacts WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM contacts")
    fun getAllObservable(): Flow<List<ContactData>>

    @Query("SELECT * FROM contacts")
    fun getAll(): List<ContactData>

    @Query("DELETE FROM contacts")
    suspend fun deleteAll()

    @Transaction
    suspend fun fullUpdate(items: List<ContactData>){
        deleteAll()
        insert(*items.toTypedArray())
    }

    @Query("SELECT * FROM contacts WHERE id = :userId")
    suspend fun get(userId: String): ContactData?

    @Query("SELECT * FROM contacts WHERE userArn = :userArn")
    suspend fun getByArn(userArn: String): ContactData?
}