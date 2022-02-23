package pro.inmost.android.visario.utils.extensions

import android.util.Patterns
import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import pro.inmost.android.visario.data.api.services.Endpoints
import pro.inmost.android.visario.utils.logE
import java.net.URLEncoder

fun String.isValidUrl() = URLUtil.isValidUrl(this)

val String.meetingId
    get() = kotlin.runCatching {
        if (isMeetingInvitation()) substringAfter('=') else null
    }.getOrNull()

/**
 * Check if a string is a valid email address
 *
 */
fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


/**
 * Do the action if string is not empty
 *
 * @param action
 * @receiver
 */
inline fun CharSequence.ifNotEmpty(action: () -> Unit) {
    if (isNotBlank()) action()
}

/**
 * Translates a string into application/x-www-form-urlencoded format using a specific encoding scheme
 *
 */
fun String.urlEncode() = kotlin.runCatching {
    URLEncoder.encode(this, "utf-8")
}.getOrDefault(this)

/**
 * Check if a string include a correct meeting invitation link
 *
 */
fun String.isMeetingInvitation() = contains(Endpoints.MEETING_GET)


fun String.toMimeType() = MimeTypeMap.getSingleton().getMimeTypeFromExtension(this)

fun String.toRequestBody(): RequestBody {
    return toRequestBody("text/plain".toMediaTypeOrNull())
}

inline fun <reified T> String.parseJson(gson: Gson? = null): T? {
    return kotlin.runCatching {
        (gson ?: Gson()).fromJson(this, T::class.java).also {
            if (it == null) {
                logE("Json for ${T::class.simpleName} class null or empty")
            }
        }
    }.onFailure {
        logE(it.message ?: "Invalid json for ${T::class.simpleName} class")
        it.printStackTrace()
    }.getOrNull()
}
