package pro.inmost.android.visario.ui.utils

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File

class MediaPicker(private val fragment: Fragment) {

    fun startImagePicker(resultLauncher: ActivityResultLauncher<Intent>){
        ImagePicker.with(fragment)
            .cropSquare()
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
}