package pro.inmost.android.visario.data.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.apache.commons.codec.digest.DigestUtils
import java.net.URLEncoder

fun String.urlEncode() = kotlin.runCatching {
    URLEncoder.encode(this, "utf-8")
}.getOrDefault(this)

fun String.sha256() = DigestUtils.sha256Hex(this)

fun launchIO(action: suspend CoroutineScope.() -> Unit): Job {
    return CoroutineScope(Dispatchers.IO).launch{ action() }
}

fun launchMain(action: suspend  CoroutineScope.() -> Unit): Job {
    return CoroutineScope(Dispatchers.Main).launch{ action() }
}