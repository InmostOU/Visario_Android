package pro.inmost.android.visario.ui.screens.channels.create

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentChannelCreateBinding
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.utils.navigateBack

class CreateChannelFragment : BaseFragment<FragmentChannelCreateBinding>(R.layout.fragment_channel_create) {
    private val viewModel: CreateChannelViewModel by viewModel()

    override fun onCreated() {
        binding.viewModel = viewModel
        observeEvents()
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
        viewModel.closeFragment.observe(viewLifecycleOwner) { navigateBack() }
    }
}