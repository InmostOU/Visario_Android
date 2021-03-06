package pro.inmost.android.visario.data.utils.extensions

import android.util.Patterns
import pro.inmost.android.visario.data.api.services.Endpoints
import java.net.URLEncoder


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
