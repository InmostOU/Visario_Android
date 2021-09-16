package pro.inmost.android.visario.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pro.inmost.android.visario.data.database.dao.*
import pro.inmost.android.visario.data.entities.channel.ChannelData
import pro.inmost.android.visario.data.entities.contact.ContactData
import pro.inmost.android.visario.data.entities.meeting.AttendeeData
import pro.inmost.android.visario.data.entities.message.MessageData
import pro.inmost.android.visario.data.entities.profile.ProfileData

@Database(
    entities = [
        ChannelData::class,
        MessageData::class,
        ContactData::class,
        ProfileData::class,
        AttendeeData::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun channelsDao(): ChannelsDao
    abstract fun messagesDao(): MessagesDao
    abstract fun contactsDao(): ContactsDao
    abstract fun profileDao(): ProfileDao
    abstract fun attendeesDao(): AttendeesDao

    companion object {
        private var databaseName: String = "visario_android_db"

        /**
         * Updates the database name when a new user login
         *
         * @param user - user identifier (like email)
         */
        fun updateName(user: String) {
            databaseName = "visario_android_db_${user.hashCode()}"
        }


        /**
         * Get new instance of database
         *
         * @param context
         */
        fun getInstance(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            databaseName
        ).build()
    }
}