package pro.inmost.android.visario.data.api.dto.responses

import android.text.format.DateFormat

data class StandardResponse (
    val timestamp: Long,
    val status: Int,
    val message: String,
    val path: String
) {

    override fun toString(): String {
        return "${status};$message;$path;${DateFormat.format("dd-MM-yyyy HH:mm:ss", timestamp)}"
    }
}
