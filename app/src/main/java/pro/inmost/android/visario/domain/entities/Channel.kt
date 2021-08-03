package pro.inmost.android.visario.domain.entities

data class Channel (
    val url: String,
    val name: String,
    val mode: ChannelMode,
    val privacy: Privacy,
    var messages: List<Message>
){
    val lastMessage: String
        get() = messages.firstOrNull()?.text ?: ""
    val lastMessageTime: String
        get() = messages.firstOrNull()?.createdDateFormat ?: ""
    val newMessagesCount: Int
        get() = messages.filter { it.read }.count()
}