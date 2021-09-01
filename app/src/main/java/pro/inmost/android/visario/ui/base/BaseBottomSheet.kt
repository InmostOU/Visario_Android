package pro.inmost.android.visario.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pro.inmost.android.visario.R

abstract class BaseBottomSheet<T : ViewDataBinding>(private val layId: Int) : BottomSheetDialogFragment() {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    override fun getTheme(): Int = R.style.CustomBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreated()
    }

    fun close(){
        dismiss()
    }

    abstract fun onCreated()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}