package pro.inmost.android.visario.ui.dialogs.media

import android.content.Context
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import pro.inmost.android.visario.ui.utils.FileScanner
import pro.inmost.android.visario.ui.utils.FilesManager
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class ImagePickerViewModel : ViewModel() {
    private var selectedImage: Uri? = null
    private val _selectedImageEvent = SingleLiveEvent<Uri>()
    val selectedImageEvent: LiveData<Uri> = _selectedImageEvent

    fun selectImage(uri: Uri){
        selectedImage = uri
        _selectedImageEvent.value = uri
    }

    fun isPhotoCaptured(captured: Boolean){
        if (captured){
            _selectedImageEvent.value = selectedImage!!
        } else {
            selectedImage = null
        }
    }

    fun loadImages(context: Context): Flow<Uri> {
        return FileScanner(context.contentResolver).scanImages()
            .filter { it.mimeType.contains("png")
                    || it.mimeType.contains("jpg", true)
                    || it.mimeType.contains("jpeg", true)
            }.map { it.uri }

    }

    fun capturePhoto(context: Context, launcher: ActivityResultLauncher<Uri>) {
        selectedImage = getTempFile(context)
        launcher.launch(selectedImage)
    }

    private fun getTempFile(context: Context): Uri {
        return FilesManager.createImageFileUri(context)
    }
}