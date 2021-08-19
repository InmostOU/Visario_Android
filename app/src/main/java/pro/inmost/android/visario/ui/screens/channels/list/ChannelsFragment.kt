package pro.inmost.android.visario.ui.screens.channels.list

import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentChannelsBinding
import pro.inmost.android.visario.databinding.ListItemChannelBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapterWithDiffUtil
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.entities.channel.ChannelUI
import pro.inmost.android.visario.ui.utils.extensions.navigate

class ChannelsFragment : BaseFragment<FragmentChannelsBinding>(R.layout.fragment_channels) {
    private val viewModel: ChannelsViewModel by viewModel()
    private val listAdapter =
        GenericListAdapterWithDiffUtil<ChannelUI, ListItemChannelBinding>(R.layout.list_item_channel) { channel, binding ->
            binding.viewModel = viewModel
            binding.model = channel
        }

    override var bottomNavViewVisibility: Int = View.VISIBLE

    override fun onCreated() {
        binding.channelList.adapter = listAdapter
        observeData()
        observeEvents()
    }

    private fun observeEvents() {
        viewModel.onChatClick.observe(viewLifecycleOwner) {
            openChannel(it)
        }
        binding.buttonNewChannel.setOnClickListener { openCreateChannelFragment() }
        binding.toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.menu_search_channels -> { openSearchChannelsFragment() }
            }
            true
        }
    }

    private fun openSearchChannelsFragment() {
        navigate {
            ChannelsFragmentDirections.actionNavigationChatsToNavigationChannelSearch()
        }
    }

    private fun openCreateChannelFragment() {
        navigate {
            ChannelsFragmentDirections.actionNavigationChatsToNavigationChannelCreate()
        }
    }

    private fun observeData() {
        viewModel.channels.observe(viewLifecycleOwner) { list ->
            listAdapter.submitList(list)
        }
    }

    private fun openChannel(channel: ChannelUI) {
        navigate {
            ChannelsFragmentDirections.actionNavigationChatsToNavigationMessages(
                channelName = channel.name,
                channelUrl = channel.url
            )
        }
    }

}