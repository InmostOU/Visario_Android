package pro.inmost.android.visario.core.data.chimeapi.messages

data class SendMessageResponse(
    val timestamp: String,
    val status: Int,
    val message: String,
    val error: String?,
    val path: String
)
