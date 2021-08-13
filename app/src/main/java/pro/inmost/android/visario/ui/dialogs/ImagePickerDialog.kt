package pro.inmost.android.visario.ui.dialogs

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
import com.esafirm.imagepicker.features.ImagePickerLauncher
import com.esafirm.imagepicker.features.registerImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.DialogImagePickerBinding
import pro.inmost.android.visario.ui.utils.*

class ImagePickerDialog(val callback: (Uri) -> Unit) : BottomSheetDialogFragment() {
    private var _binding: DialogImagePickerBinding? = null
    private val binding get() = _binding!!

    private val profileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    setResultAndClose(fileUri)
                }
                else -> {
                    log("image picker: ImagePicker.getError(data)")
                    dismiss()
                }
            }
        }

    private lateinit var launcher: ImagePickerLauncher

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcher = registerImagePicker {
            val uri = it[0].uri
            setResultAndClose(uri)
        }
    }



    override fun getTheme(): Int = R.style.CustomBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogImagePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeDialog.setOnClickListener { dismiss() }
        binding.gallery.setOnClickListener { startGalleryPicker() }
        binding.takePhoto.setOnClickListener { startCameraPicker() }
    }

    private fun startGalleryPicker() {
        MediaPicker(this).pickFromGallery(profileImageResult)
    }

    private fun startCameraPicker() {
       // MediaPicker(this).takePhoto(profileImageResult)
    }

    private fun setResultAndClose(uri: Uri) {
        callback(uri)
        dismiss()
    }

    companion object {
        fun show(fragmentManager: FragmentManager, callback: (Uri) -> Unit){
            ImagePickerDialog(callback).show(fragmentManager, null)
        }
    }
}