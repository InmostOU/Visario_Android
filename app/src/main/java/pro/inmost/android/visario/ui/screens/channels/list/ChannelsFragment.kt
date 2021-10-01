package pro.inmost.android.visario.ui.screens.channels.list

import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentChannelsBinding
import pro.inmost.android.visario.databinding.ListItemChannelBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapterWithDiffUtil
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.entities.channel.ChannelUI
import pro.inmost.android.visario.utils.extensions.navigate


/**
 * Fragment to show user's list of channels
 *
 */
class ChannelsFragment : BaseFragment<FragmentChannelsBinding>(R.layout.fragment_channels) {
    private val viewModel: ChannelsViewModel by viewModel()
    private val listAdapter =
        GenericListAdapterWithDiffUtil<ChannelUI, ListItemChannelBinding>(R.layout.list_item_channel) { channel, binding ->
            binding.viewModel = viewModel
            binding.model = channel
        }

    override var bottomNavViewVisibility: Int = View.VISIBLE

    override fun onCreated() {
        binding.viewModel = viewModel
        binding.channelList.adapter = listAdapter
        observeData()
        observeEvents()
    }

    private fun observeEvents() {
        viewModel.onChannelClick.observe(viewLifecycleOwner) { channelArn ->
            openChannel(channelArn)
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
        showProgressBar()
        viewModel.channels.observe(viewLifecycleOwner) { list ->
            hideProgressBar()
            listAdapter.submitList(list)
        }
    }

    private fun hideProgressBar() {
        if (binding.progressBar.isShown){
            binding.progressBar.hide()
        }
    }

    private fun showProgressBar() {
        if (!binding.progressBar.isShown){
            binding.progressBar.show()
        }
    }

    private fun openChannel(channelArn: String) {
        navigate {
            ChannelsFragmentDirections.actionNavigationChatsToNavigationMessages(channelArn)
        }
    }

}