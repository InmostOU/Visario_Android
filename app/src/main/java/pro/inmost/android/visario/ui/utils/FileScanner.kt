package pro.inmost.android.visario.ui.utils

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import kotlinx.coroutines.flow.flow


/**
 * Scan user's device for images
 *
 * @property contentResolver [ContentResolver]
 */
class FileScanner(private val contentResolver: ContentResolver) {

    enum class Sort {
        ASC,
        DESC
    }
    data class Data (
        val uri: Uri,
        val name: String,
        val dateAdded: Long,
        val dateModified: Long,
        val size: Long,
        val mimeType: String
    )


    /**
     * Start scan device
     *
     * @param sort ascending or descending (default) order
     * @return Flow that returns files one by one
     */
    fun scanImages(sort: Sort = Sort.DESC) = flow {
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

        val projection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.RELATIVE_PATH,
                MediaStore.Images.Media.MIME_TYPE
            )
        } else {
            arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.MIME_TYPE
            )
        }

        val sortOrder = when(sort){
            Sort.ASC -> "${MediaStore.Images.Media.DATE_ADDED} ASC"
            Sort.DESC -> "${MediaStore.Images.Media.DATE_ADDED} DESC"
        }

        contentResolver.query(
            collection,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            kotlin.runCatching {
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
                val dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                val mimeTypeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val displayName = cursor.getString(nameColumn)
                    val size = cursor.getInt(sizeColumn).toLong()
                    val dateAdded = cursor.getLong(dateAddedColumn)
                    val dateModified = cursor.getLong(dateModifiedColumn)
                    val type = cursor.getString(mimeTypeColumn)

                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    val data = Data(
                        uri = contentUri,
                        name = displayName,
                        size = size,
                        dateAdded = dateAdded,
                        dateModified = dateModified,
                        mimeType = type
                    )
                    emit(data)
                }
            }
        }
    }
}