package pro.inmost.android.visario.ui.dialogs.media

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.livinglifetechway.k4kotlin.core.saveFile
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentImageResizerBinding
import pro.inmost.android.visario.ui.utils.FilesManager
import pro.inmost.android.visario.ui.utils.extensions.biggerThan
import pro.inmost.android.visario.ui.utils.extensions.getUri
import java.io.File

class ImageResizerDialog(private val imageForCrop: Uri, val callback: (Uri) -> Unit) :
    DialogFragment(R.layout.fragment_image_resizer) {
    private var _binding: FragmentImageResizerBinding? = null
    private val binding get() = _binding!!

    override fun getTheme(): Int = android.R.style.ThemeOverlay

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageResizerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            cropView.setImageUriAsync(imageForCrop)
            buttonDone.setOnClickListener {
                lifecycleScope.launch {
                    val image = binding.cropView.getCroppedImage(1080, 1080)
                    val destUri = saveToFile(image)
                    setResultAndClose(destUri)
                }
            }
            buttonRotate.setOnClickListener {
                binding.cropView.rotateImage(90)
            }
            toolbar.setNavigationOnClickListener { dismiss() }
        }
    }

    private suspend fun saveToFile(image: Bitmap?): Uri {
        val destFile = FilesManager.createImageFile(requireContext())
        image?.saveFile(destFile, Bitmap.CompressFormat.JPEG, 90)
        return if (destFile.biggerThan(5000)){
            decreaseSize(destFile).getUri(requireContext())
        } else {
            destFile.getUri(requireContext())
        }
    }

    private suspend fun decreaseSize(file: File): File {
        return Compressor.compress(requireContext(), file) {
            resolution(1080, 1080)
            quality(80)
            size(4_194_305) // 4 MB
        }
    }

    private fun setResultAndClose(uri: Uri) {
        callback(uri)
        dismiss()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        fun show(fragmentManager: FragmentManager, imageUri: Uri, callback: (Uri) -> Unit) {
            ImageResizerDialog(imageUri, callback).show(fragmentManager, null)
        }
    }
}