package pro.inmost.android.visario.ui.utils

import android.content.Context
import android.net.Uri
import android.text.format.DateFormat
import androidx.core.content.FileProvider
import java.io.File

object FilesManager {

    fun createImageFileUri(context: Context): Uri {
        return createImageFile(context).getUri(context)
    }

    fun createImageFile(context: Context): File {
        val timeStamp = DateFormat.format("yyyyMMdd_hh-mm-ss", System.currentTimeMillis())
        val storageDir = File(context.cacheDir, "Photos")
        if (!storageDir.exists()) storageDir.mkdirs()

        return File.createTempFile(
            "IMG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }
}

fun File.getUri(context: Context) = FileProvider.getUriForFile(context, FILE_PROVIDER_AUTH, this)