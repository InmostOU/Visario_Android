package pro.inmost.android.visario.data.api.dto.responses.base

import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.utils.logError

data class BaseDataResponse<T>(
    val status: Int,
    val message: String,
    val data: T
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
