package pro.inmost.android.visario.core.domain.entities.chat

data class Channel(
    val channelArn: String,
    val metadata: String?,
    val mode: String?,
    val name: String?,
    val privacy: String?
)