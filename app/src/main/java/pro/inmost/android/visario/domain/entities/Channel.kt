package pro.inmost.android.visario.domain.entities

data class Channel (
    val url: String,
    val name: String,
    val mode: ChannelMode,
    val privacy: Privacy,
    var messages: List<Message>
)