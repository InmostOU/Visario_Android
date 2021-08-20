package pro.inmost.android.visario.data.api.dto.requests.channels

data class CreateChannelRequest(
    val name: String,
    val description: String,
    val privacy: String,
    val mode: String,
    var metadata: String = "metadata"
)