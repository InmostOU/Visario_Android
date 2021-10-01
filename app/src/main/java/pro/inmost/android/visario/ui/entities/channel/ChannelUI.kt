package pro.inmost.android.visario.ui.entities.channel

import androidx.databinding.ObservableBoolean
import pro.inmost.android.visario.ui.entities.BaseEntity
import pro.inmost.android.visario.ui.entities.message.MessageUI
import pro.inmost.android.visario.utils.ONE_DAY_IN_MILLIS
import pro.inmost.android.visario.utils.ONE_WEEK_IN_MILLIS

data class ChannelUI (
    val channelArn: String,
    val name: String,
    val mode: ChannelMode,
    val privacy: ChannelPrivacy,
    val description: String,
    val isMember: Boolean,
    val isModerator: Boolean,
    val isAdmin: Boolean,
    val membersCount: Int,
    var messages: List<MessageUI> = listOf()
): BaseEntity {

    val lastMessage: String
        get() = messages.firstOrNull()?.text?.trim() ?: ""

    val hasMessages: Boolean
        get() = messages.isNotEmpty()

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

    val isLastMessageMeetingInvitation: Boolean
        get() = messages.firstOrNull()?.isMeetingInvitation ?: false

    val isLastMessageEmpty: Boolean
        get() = messages.firstOrNull()?.text?.trim().isNullOrBlank()

    val isLastMessageHasAttachmentAndNoText: Boolean
        get() = messages.firstOrNull()?.attachment != null && messages.firstOrNull()?.text.isNullOrBlank()

    val disabled = ObservableBoolean(false)
    val invited = ObservableBoolean(false)

    override val baseId: String
        get() = channelArn
}