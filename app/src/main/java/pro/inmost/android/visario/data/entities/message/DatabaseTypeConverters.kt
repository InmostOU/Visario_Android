package pro.inmost.android.visario.data.entities.message

import androidx.room.TypeConverter
import com.google.gson.Gson
import pro.inmost.android.visario.data.api.dto.requests.messages.AttachmentData

class DatabaseTypeConverters {
    @TypeConverter
    fun stringFromAttachment(value: AttachmentData): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun attachmentFromString(value: String): AttachmentData {
        return Gson().fromJson(value, AttachmentData::class.java)
    }
}