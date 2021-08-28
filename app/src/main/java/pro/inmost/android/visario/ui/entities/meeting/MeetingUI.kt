package pro.inmost.android.visario.ui.entities.meeting

import android.text.format.DateFormat
import pro.inmost.android.visario.ui.utils.MEETING_DATE_FORMAT

data class MeetingUI(
    val id: Int,
    val name: String,
    val url: String,
    val date: Long
){
    val dateFormat: String
        get() =  DateFormat.format(MEETING_DATE_FORMAT, date).toString()
}
