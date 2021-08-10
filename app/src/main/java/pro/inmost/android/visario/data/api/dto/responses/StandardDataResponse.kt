package pro.inmost.android.visario.data.api.dto.responses

data class StandardDataResponse<T>(
    val status: Int,
    val message: String,
    val data: List<T>
)
