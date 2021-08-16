package pro.inmost.android.visario.data.api.dto.requests

data class CreateChannelRequest(
    val name: String,
    val privacy: String,
    val mode: String,
    var metadata: String = ""
)