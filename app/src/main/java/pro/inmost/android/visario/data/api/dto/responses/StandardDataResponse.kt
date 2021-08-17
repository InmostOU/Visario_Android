package pro.inmost.android.visario.data.api.dto.responses

import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.utils.logError

data class StandardDataResponse<T>(
    val status: Int,
    val message: String,
    val data: List<T>
){
    fun getResult() = if (status == ChimeApi.STATUS_OK){
        Result.success(data)
    } else {
        logError(toString())
        Result.failure(Throwable(toString()))
    }

    override fun toString(): String {
        return "${status}: $message}"
    }
}
