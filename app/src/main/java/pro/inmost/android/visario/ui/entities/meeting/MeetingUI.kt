package pro.inmost.android.visario.ui.entities.meeting

import android.text.format.DateFormat
import com.amazonaws.services.chime.sdk.meetings.session.MeetingSession
import pro.inmost.android.visario.ui.utils.MEETING_DATE_FORMAT
import java.util.*

data class MeetingUI(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val url: String = "",
    val date: Long = System.currentTimeMillis(),
    val session: MeetingSession? = null
){
    val dateFormat: String
        get() =  DateFormat.format(MEETING_DATE_FORMAT, date).toString()
}
