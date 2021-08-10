package pro.inmost.android.visario.data.api.dto.responses

data class StandardResponse(
    val timestamp: Long,
    val status: Int,
    val message: String,
    val path: String
)
