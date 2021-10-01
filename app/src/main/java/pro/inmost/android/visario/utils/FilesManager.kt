package pro.inmost.android.visario.ui.utils

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.format.DateFormat
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.R
import pro.inmost.android.visario.utils.extensions.getUri
import pro.inmost.android.visario.utils.extensions.toast
import pro.inmost.android.visario.utils.log
import java.io.File


/**
 * Helper class for creating files
 *
 */
object FilesManager {
    private val DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

    fun createImageFileUri(context: Context): Uri {
        return createImageFile(context).getUri(context)
    }

    fun createImageFile(context: Context): File {
        val timeStamp = DateFormat.format("yyyyMMdd_hh-mm-ss", System.currentTimeMillis())
        val storageDir = File(context.filesDir, "Photos")
        if (!storageDir.exists()) storageDir.mkdirs()

        return File.createTempFile(
            "IMG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    fun createTempFile(context: Context, name: String, extension: String): File {
        val storageDir = File(context.filesDir, "Other")
        if (!storageDir.exists()) storageDir.mkdirs()
        return File(storageDir, "$name.$extension").also { it.createNewFile() }
    }

    suspend fun saveInDownloads(context: Context, url: String, name: String, extension: String) = withContext(Dispatchers.IO){
        val direct = File(context.externalCacheDir, "Files")
        log("file url: $url, fileName: $name, fileExt: $extension")
        if (!direct.exists()) {
            direct.mkdirs()
        }

        val dm: DownloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
            "$name.$extension"
        )
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setTitle("File downloading")
        withContext(Dispatchers.Main) { context.toast(R.string.downloading) }
        val downloadReference: Long = dm.enqueue(request)
    }

    fun copyFileToDownloads(context: Context, downloadedFile: File): Uri? {
        val resolver = context.contentResolver
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, downloadedFile.name)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/*")
                put(MediaStore.MediaColumns.SIZE, downloadedFile.length())
            }
            resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        } else {
            val authority = "${context.packageName}.provider"
            val destinyFile = File(DOWNLOAD_DIR, downloadedFile.name)
            FileProvider.getUriForFile(context, authority, destinyFile)
        }?.also { downloadedUri ->
            resolver.openOutputStream(downloadedUri)?.use { outputStream ->
                outputStream.write(downloadedFile.readBytes())
            }
        }
    }

    fun saveToCache(context: Context, uri: Uri): String? {
        val type = context.contentResolver.getType(uri) ?: ""
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(type) ?: ""
        val fileName = context.getFileName(uri).split(".")[0]
        val destFile = createTempFile(context, fileName, extension)

        return context.contentResolver.openInputStream(uri)?.use { stream ->
            destFile.writeBytes( stream.readBytes())
            destFile.absolutePath
        }
    }

    @SuppressLint("Range")
    fun Context.getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            cursor.use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result!!.substring(cut + 1)
            }
        }
        return result ?: "File_" + System.currentTimeMillis()
    }
}