package pro.inmost.android.visario.domain.entities

data class Channel (
    val url: String,
    val name: String,
    val mode: ChannelMode,
    val privacy: Privacy,
    val messages: List<Message>
){
    val lastMessage: String
        get() = messages.first().text
    val lastMessageTime: String
        get() = messages.first().createdDateFormat
    val newMessagesCount: Int
        get() = messages.filter { it.read }.count()
}