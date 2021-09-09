package pro.inmost.android.visario.ui.utils.extensions

import android.webkit.URLUtil
import pro.inmost.android.visario.data.utils.extensions.isMeetingInvitation

fun String.isValidUrl() =  URLUtil.isValidUrl(this)

val String.meetingId get() = kotlin.runCatching {
    if (isMeetingInvitation()) substringAfter('=') else null
}.getOrNull()