package pro.inmost.android.visario.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pro.inmost.android.visario.data.database.dao.ChannelsDao
import pro.inmost.android.visario.data.database.dao.MessagesDao
import pro.inmost.android.visario.data.entities.ChannelData
import pro.inmost.android.visario.data.entities.MessageData

@Database(
    entities = [
        ChannelData::class,
        MessageData::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun channelsDao(): ChannelsDao
    abstract fun messagesDao(): MessagesDao

    companion object {
        private const val DATABASE_NAME = "visario_android_db"
        lateinit var instance: AppDatabase

        fun init (context: Context, user: String) {
            instance = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "${DATABASE_NAME}_${user.hashCode()}"
            ).build()
        }
    }
}