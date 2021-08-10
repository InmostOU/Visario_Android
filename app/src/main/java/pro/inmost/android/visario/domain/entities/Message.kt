package pro.inmost.android.visario.domain.entities

import android.text.format.DateFormat

data class Message(
    val messageId: String,
    val text: String,
    val channelUrl: String,
    val senderUrl: String,
    val senderName: String,
    val createdTimestamp: Long,
    val type: String,
    val redacted: Boolean = false,
    val lastEditedTimestamp: Long = 0,
    val fromCurrentUser: Boolean = false,
    val read: Boolean = false
)