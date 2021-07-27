package pro.inmost.android.visario.core.data.chimeapi.channels.model

data class Channel(
    val channelArn: String,
    val name: String?,
    val mode: String?,
    val privacy: String?,
    val metadata: String?
)