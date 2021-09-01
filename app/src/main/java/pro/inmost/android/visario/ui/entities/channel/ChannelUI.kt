package pro.inmost.android.visario.ui.entities.channel

import pro.inmost.android.visario.ui.entities.BaseEntity
import pro.inmost.android.visario.ui.entities.message.MessageUI
import pro.inmost.android.visario.ui.utils.ONE_DAY_IN_MILLIS
import pro.inmost.android.visario.ui.utils.ONE_WEEK_IN_MILLIS

data class ChannelUI (
    val url: String,
    val name: String,
    val mode: ChannelMode,
    val privacy: ChannelPrivacy,
    val description: String,
    val isMember: Boolean,
    val isModerator: Boolean,
    var messages: List<MessageUI> = listOf()
): BaseEntity {
    val lastMessage: String
        get() = messages.firstOrNull()?.text ?: ""
    val lastMessageTime: String
        get() {
            val lastMessage = messages.firstOrNull() ?: return ""
            val timeDiff = System.currentTimeMillis() - lastMessage.createdTimestamp
            return when {
                timeDiff > ONE_WEEK_IN_MILLIS -> lastMessage.createdDateFormat
                timeDiff > ONE_DAY_IN_MILLIS -> lastMessage.createdDayFormat
                else -> lastMessage.createdTimeFormat
            }
        }
    val newMessagesCount: Int
        get() = messages.count { !it.readByMe }


    override val baseId: String
        get() = url
}