package pro.inmost.android.visario.ui.screens.channels.info

import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentChannelInfoBinding
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.entities.channel.ChannelUI
import pro.inmost.android.visario.ui.utils.extensions.navigate
import pro.inmost.android.visario.ui.utils.extensions.navigateBack

class ChannelInfoFragment : BaseFragment<FragmentChannelInfoBinding>(R.layout.fragment_channel_info) {
    private val viewModel: ChannelInfoViewModel by viewModel()
    private val args: ChannelInfoFragmentArgs by navArgs()

    override fun onCreated() {
        binding.viewModel = viewModel
        observeData()
        observeEvents()
    }

    private fun observeData() {
        viewModel.loadChannel(args.channelArn).observe(viewLifecycleOwner){ channel ->
            binding.model = channel
            binding.executePendingBindings()
        }
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
        viewModel.openMemberListEvent.observe(viewLifecycleOwner){ channel ->
            openMemberList(channel)
        }
    }

    private fun openMemberList(channel: ChannelUI) {
        navigate {
            ChannelInfoFragmentDirections.actionNavigationChannelInfoToNavigationChannelMembers(channel.url, channel.isModerator)
        }
    }
}
