package pro.inmost.android.visario.ui.entities.channel

import pro.inmost.android.visario.ui.entities.message.MessageUI

data class ChannelUI (
    val url: String,
    val name: String,
    val mode: ChannelMode,
    val privacy: ChannelPrivacy,
    val description: String,
    val isMember: Boolean,
    val isModerator: Boolean,
    var messages: List<MessageUI> = listOf()
){
    val lastMessage: String
        get() = messages.firstOrNull()?.text ?: ""
    val lastMessageTime: String
        get() = messages.firstOrNull()?.createdDateFormat ?: ""
    val newMessagesCount: Int
        get() = messages.count { it.read }
    val hasNewMessages: Boolean
        get() = newMessagesCount > 0
}