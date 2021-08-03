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
        private var databaseName: String = "visario_android_db"

        fun updateName(user: String){
            databaseName = "visario_android_db_${user.hashCode()}"
        }

        fun getInstance (context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            databaseName
        ).build()
    }
}