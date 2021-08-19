package pro.inmost.android.visario.data.api.dto.responses.base

import android.text.format.DateFormat
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.utils.logError

data class BaseResponse (
    val timestamp: Long,
    val status: Int,
    val message: String,
    val path: String
) {

    fun getResult() = if (status == ChimeApi.STATUS_OK){
        Result.success(Unit)
    } else {
        logError(toString())
        Result.failure(Throwable(toString()))
    }

    override fun toString(): String {
        return "${status}: $message;$path;${DateFormat.format("dd-MM-yyyy HH:mm:ss", timestamp)}"
    }
}
