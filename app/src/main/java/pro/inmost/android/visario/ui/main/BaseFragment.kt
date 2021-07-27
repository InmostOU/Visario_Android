package pro.inmost.android.visario.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import pro.inmost.android.visario.databinding.FragmentMessagesBinding

abstract class BaseFragment<T : ViewDataBinding>(private val layId: Int) : Fragment() {
    private var _binding: T? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layId, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreated(binding)
    }

    abstract fun onCreated(binding: T)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}