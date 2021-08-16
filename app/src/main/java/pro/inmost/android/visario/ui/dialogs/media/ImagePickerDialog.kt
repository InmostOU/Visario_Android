package pro.inmost.android.visario.ui.dialogs.media

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentImagePickerBinding
import pro.inmost.android.visario.databinding.ListItemImageBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapter

class ImagePickerDialog(private val crop: Boolean, val callback: (Uri) -> Unit) : BottomSheetDialogFragment() {
    private var _binding: FragmentImagePickerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ImagePickerViewModel by viewModel()
    private val listAdapter =
        GenericListAdapter<Uri, ListItemImageBinding>(R.layout.list_item_image) { uri, binding ->
            binding.viewModel = viewModel
            binding.uri = uri
        }

    private val cameraImageLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { saved ->
            viewModel.isPhotoCaptured(saved)
        }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageList.adapter = listAdapter
        observeEvents()
        observeData()
    }

    private fun observeEvents() {
        binding.closeDialog.setOnClickListener { dismiss() }
        binding.openCamera.setOnClickListener { openCamera() }
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.loadImages(requireContext()).collect {
                listAdapter.addItem(it)
            }
        }
        viewModel.selectedImageEvent.observe(viewLifecycleOwner){
            if (crop){
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
        ImageResizerDialog.show(childFragmentManager, image){ result ->
            setResultAndClose(result)
        }
    }

    private fun setResultAndClose(uri: Uri) {
        callback(uri)
        dismiss()
    }

    companion object {
        fun show(fragmentManager: FragmentManager, crop: Boolean, callback: (Uri) -> Unit) {
            ImagePickerDialog(crop, callback).show(fragmentManager, null)
        }
    }
}