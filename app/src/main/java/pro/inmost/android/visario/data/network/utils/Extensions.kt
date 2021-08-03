package pro.inmost.android.visario.data.network.utils

import java.net.URLEncoder

fun String.urlEncode() = kotlin.runCatching {
    URLEncoder.encode(this, "utf-8")
}.getOrDefault(this)