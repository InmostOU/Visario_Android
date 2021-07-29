package pro.inmost.android.visario.core.data.chimeapi.messages

import android.text.format.DateFormat
import pro.inmost.android.visario.ui.utils.DateParser

data class Message(
    val messageId: String,
    val content: String,
    val createdTimestamp: String,
    val lastEditedTimestamp: String,
    val metadata: String,
    val redacted: Boolean,
    val senderArn: String,
    val senderName: String,
    val type: String,
    val fromCurrentUser: Boolean = false
) {
    fun getFormatDate(): String {
        val millis = DateParser.parseToMillis(createdTimestamp)
        return DateFormat.format("hh:mm", millis).toString()
    }
}