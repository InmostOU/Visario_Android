package pro.inmost.android.visario.ui.utils

import android.content.Intent
import android.graphics.Color
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.esafirm.imagepicker.features.*
import com.github.dhaval2404.imagepicker.ImagePicker
import pro.inmost.android.visario.R
import java.io.File

class MediaPicker(private val fragment: Fragment) {

    fun pickFromGallery(resultLauncher: ActivityResultLauncher<Intent>){
        ImagePicker.with(fragment)
            .cropSquare()
            .galleryOnly()
            .compress(1024)         //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
            .saveDir(File(fragment.requireActivity().cacheDir, CACHED_PROFILE_IMAGES_DIR))
            .galleryMimeTypes(  //Exclude gif images
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            .createIntent { intent ->
                resultLauncher.launch(intent)
            }
    }

    fun takePhoto(resultLauncher: ActivityResultLauncher<Intent>){
        ImagePicker.with(fragment)
            .cropSquare()
            .cameraOnly()
            .compress(1024)         //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
            .saveDir(File(fragment.requireActivity().cacheDir, CACHED_PROFILE_IMAGES_DIR))
            .createIntent { intent ->
                resultLauncher.launch(intent)
            }
    }

    fun pickImage(launcher: ImagePickerLauncher){
        val config = ImagePickerConfig {
            mode = ImagePickerMode.SINGLE // default is multi image mode
            language = "in" // Set image picker language
            theme = R.style.Theme_Visario

            // set whether pick action or camera action should return immediate result or not. Only works in single mode for image picker
         //   returnMode = if (returnAfterCapture) ReturnMode.ALL else ReturnMode.NONE
            isSaveImage = true
            isFolderMode = false // set folder mode (false by default)
            isIncludeVideo = false // include video (false by default)
            isOnlyVideo = false // include video (false by default)
            arrowColor = Color.WHITE // set toolbar arrow up color
            folderTitle = "Folder" // folder selection title
            imageTitle = "Tap to select" // image selection title
            doneButtonText = "DONE" // done button text
            limit = 10 // max images can be selected (99 by default)
            isShowCamera = true // show camera or not (true by default)
            savePath = ImagePickerSavePath(File(fragment.requireActivity().cacheDir, CACHED_PROFILE_IMAGES_DIR).absolutePath, isRelative = false) // can be a full path
        }
        launcher.launch(config)
    }
}