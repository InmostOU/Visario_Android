package pro.inmost.android.visario.data.utils.extensions

import android.util.Patterns
import org.apache.commons.codec.digest.DigestUtils
import pro.inmost.android.visario.data.api.services.Endpoints
import java.net.URLEncoder

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


inline fun CharSequence.ifNotEmpty(action: () -> Unit) {
    if (isNotBlank()) action()
}

fun String.urlEncode() = kotlin.runCatching {
    URLEncoder.encode(this, "utf-8")
}.getOrDefault(this)

fun String.sha256() = DigestUtils.sha256Hex(this)

fun String.isMeetingInvitation() = contains(Endpoints.MEETING_GET)
