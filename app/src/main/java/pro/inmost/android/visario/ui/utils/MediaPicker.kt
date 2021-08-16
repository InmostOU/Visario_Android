package pro.inmost.android.visario.ui.utils

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options

class MediaPicker(private val fragment: Fragment) {

    fun pickFromGallery(resultLauncher: ActivityResultLauncher<Intent>){

    }

    fun takePhoto(resultLauncher: ActivityResultLauncher<Intent>){

    }

    fun pickImage(launcher: ActivityResultLauncher<CropImageContractOptions>){
        // start picker to get image for cropping and then use the image in cropping activity
        launcher.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
            }
        )
    }
}