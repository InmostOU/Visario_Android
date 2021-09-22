package pro.inmost.android.visario.ui.screens.channels.search

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentChannelsSearchBinding
import pro.inmost.android.visario.databinding.ListItemChannelFoundBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapterWithDiffUtil
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.entities.channel.ChannelUI
import pro.inmost.android.visario.ui.utils.extensions.navigate
import pro.inmost.android.visario.ui.utils.extensions.navigateBack
import pro.inmost.android.visario.ui.utils.extensions.onQueryChange
import pro.inmost.android.visario.ui.utils.hideKeyboard


/**
 * Fragment for search public channels by name
 *
 */
class SearchChannelsFragment : BaseFragment<FragmentChannelsSearchBinding>(R.layout.fragment_channels_search) {
    private val viewModel: SearchChannelsViewModel by viewModel()
    private val listAdapter =
        GenericListAdapterWithDiffUtil<ChannelUI, ListItemChannelFoundBinding>(R.layout.list_item_channel_found) { channel, binding ->
            binding.viewModel = viewModel
            binding.model = channel
        }

    override fun onCreated() {
        binding.viewModel = viewModel
        binding.channelList.adapter = listAdapter
        observeEvents()
        observeData()
    }

    private fun observeData() {
        viewModel.foundChannels.observe(viewLifecycleOwner){
            listAdapter.submitList(it)
        }
    }

    private fun observeEvents() {
        binding.searchView.onQueryChange { viewModel.search(it) }
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
        viewModel.openChannel.observe(viewLifecycleOwner){ channel ->
            openChannel(channel)
        }
    }

    private fun openChannel(channel: ChannelUI) {
        hideKeyboard()
        navigate {
            SearchChannelsFragmentDirections.actionNavigationChannelSearchToNavigationMessages(
                channelName = channel.name,
                channelUrl = channel.url,
                isMember = channel.isMember,
                isModerator = channel.isModerator
            )
        }
    }
}