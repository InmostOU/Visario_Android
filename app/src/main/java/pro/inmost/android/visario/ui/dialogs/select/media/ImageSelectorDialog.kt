package pro.inmost.android.visario.ui.dialogs.select.media

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.BottomSheetSelectImageBinding
import pro.inmost.android.visario.databinding.ListItemImageBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapter
import pro.inmost.android.visario.ui.base.BaseBottomSheet

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
                setResultAndClose(it)
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

    companion object {
        fun show(fragmentManager: FragmentManager, crop: Boolean, callback: (Uri) -> Unit) {
            ImageSelectorDialog(crop, callback).show(fragmentManager, null)
        }
    }
}