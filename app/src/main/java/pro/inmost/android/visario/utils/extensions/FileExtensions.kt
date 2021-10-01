package pro.inmost.android.visario.utils.extensions

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import pro.inmost.android.visario.utils.FILE_PROVIDER_AUTH
import java.io.File

fun File.getUri(context: Context) = FileProvider.getUriForFile(context, FILE_PROVIDER_AUTH, this)

fun File.biggerThan(kilobytes: Int) = length() / 1024 > kilobytes
fun File.lessThan(kilobytes: Int) = length() / 1024 < kilobytes

fun Uri.toFile(context: Context) = File(context.filesDir.absolutePath.removeSuffix("files") + path?.removePrefix("/"))
fun Uri.toAbsolutePath(context: Context) = context.filesDir.absolutePath.removeSuffix("files") + path?.removePrefix("/")