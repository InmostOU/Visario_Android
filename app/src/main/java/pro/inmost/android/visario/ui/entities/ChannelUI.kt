package pro.inmost.android.visario.ui.entities

data class ChannelUI (
    val url: String,
    val name: String,
    val mode: ChannelMode,
    val privacy: ChannelPrivacy,
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