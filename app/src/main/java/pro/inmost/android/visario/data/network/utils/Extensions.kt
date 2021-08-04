package pro.inmost.android.visario.data.network.utils

import org.apache.commons.codec.digest.DigestUtils
import java.net.URLEncoder

fun String.urlEncode() = kotlin.runCatching {
    URLEncoder.encode(this, "utf-8")
}.getOrDefault(this)

fun String.sha256() = DigestUtils.sha256Hex(this)