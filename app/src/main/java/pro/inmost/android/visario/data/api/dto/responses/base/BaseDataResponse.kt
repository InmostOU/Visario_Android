package pro.inmost.android.visario.data.api.dto.responses.base

import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.utils.logError

/**
 * Base response from server with extra data
 *
 * @property status - 200 - is OK
 * @property message - message about successfully or failed request
 * @property data - extra data
 */
data class BaseDataResponse<T>(
    val status: Int,
    val message: String,
    val data: T
){
    /**
     * Get result with data or Throwable object
     *
     */
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
