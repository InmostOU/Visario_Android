package pro.inmost.android.visario.ui.screens.channels.create

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentChannelCreateBinding
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.utils.extensions.navigateBack


/**
 * Fragment for create new channel
 *
 * @constructor Create empty Create channel fragment
 */
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