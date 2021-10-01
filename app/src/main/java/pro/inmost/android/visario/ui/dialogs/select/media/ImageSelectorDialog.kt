package pro.inmost.android.visario.ui.dialogs.select.media

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.BottomSheetSelectImageBinding
import pro.inmost.android.visario.databinding.ListItemImageBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapter
import pro.inmost.android.visario.ui.base.BaseBottomSheet
import pro.inmost.android.visario.ui.utils.FilesManager
import pro.inmost.android.visario.utils.extensions.biggerThan
import pro.inmost.android.visario.utils.extensions.getUri
import java.io.File

/**
 * Dialog with list of all images from user's device
 *
 * @property crop true if need to open crop window after selecting the image
 * @property callback return [Uri] to selected image
 */
class ImageSelectorDialog(private val crop: Boolean, val callback: (Uri) -> Unit) :
    BaseBottomSheet<BottomSheetSelectImageBinding>(R.layout.bottom_sheet_select_image) {
    private val viewModel: ImageSelectorViewModel by viewModel()
    private val listAdapter =
        GenericListAdapter<Uri, ListItemImageBinding>(R.layout.list_item_image) { uri, binding ->
            binding.viewModel = viewModel
            binding.uri = uri
        }

    private val cameraImageLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { saved ->
            viewModel.isPhotoCaptured(saved)
        }

    override fun onCreated() {
        binding.imageList.adapter = listAdapter
        observeEvents()
        observeData()
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { dismiss() }
        binding.openCamera.setOnClickListener { openCamera() }
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.loadImages(requireContext()).collect {
                listAdapter.addItem(it)
            }
        }
        viewModel.selectedImageEvent.observe(viewLifecycleOwner) {
            if (crop) {
                resizeImage(it)
            } else {
                lifecycleScope.launch {
                    saveToCache(it)?.let { destUri ->
                        setResultAndClose(destUri)
                    }
                }
            }
        }

    }

    private fun openCamera() {
        viewModel.capturePhoto(requireContext(), cameraImageLauncher)
    }

    private fun resizeImage(image: Uri) {
        ImageResizerDialog.show(childFragmentManager, image) { result ->
            setResultAndClose(result)
        }
    }

    private fun setResultAndClose(uri: Uri) {
        callback(uri)
        dismiss()
    }

    private suspend fun saveToCache(src: Uri): Uri? {
        val destFile = requireContext().contentResolver.openInputStream(src)?.use { stream ->
            val destFile = FilesManager.createImageFile(requireContext())
            destFile.writeBytes(stream.readBytes())
            destFile
        }
        return if (destFile != null && destFile.biggerThan(5000)){
            decreaseSize(destFile).getUri(requireContext())
        } else {
            destFile?.getUri(requireContext())
        }
    }

    private suspend fun decreaseSize(file: File): File {
        return Compressor.compress(requireContext(), file) {
            resolution(1080, 1080)
            quality(80)
            size(4_194_305) // 4 MB
        }
    }

    companion object {
        fun show(fragmentManager: FragmentManager, crop: Boolean, callback: (Uri) -> Unit) {
            ImageSelectorDialog(crop, callback).show(fragmentManager, null)
        }
    }
}