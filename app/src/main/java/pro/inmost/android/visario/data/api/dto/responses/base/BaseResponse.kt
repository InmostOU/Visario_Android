package pro.inmost.android.visario.data.api.dto.responses.base

import android.text.format.DateFormat
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.utils.logError

/**
 * Base response from server without extra data
 *
 * @property timestamp - time of response
 * @property status - 200 - is OK
 * @property message - message about successfully or failed request
 * @property path - the endpoint to which the request was sent
 */
data class BaseResponse (
    val timestamp: Long,
    val status: Int,
    val error: String?,
    val message: String,
    val path: String
) {

    /**
     *
     * Get result with data or Throwable object
     *
     */

    fun getResult() = if (status == ChimeApi.STATUS_OK){
        Result.success(Unit)
    } else {
        logError(toString())
        Result.failure(Throwable(message))
    }

    override fun toString(): String {
        return "${status}: $message;$path;${DateFormat.format("dd-MM-yyyy HH:mm:ss", timestamp)}"
    }
}
