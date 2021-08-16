package pro.inmost.android.visario.ui.screens.channels

import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentChannelsBinding
import pro.inmost.android.visario.databinding.ListItemChatBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapterWithDiffUtil
import pro.inmost.android.visario.ui.entities.ChannelUI
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.utils.navigate

class ChannelsFragment : BaseFragment<FragmentChannelsBinding>(R.layout.fragment_channels) {
    private val viewModel: ChannelsViewModel by viewModel()
    private val listAdapter =
        GenericListAdapterWithDiffUtil<ChannelUI, ListItemChatBinding>(R.layout.list_item_chat) { chat, binding ->
            binding.viewModel = viewModel
            binding.model = chat
        }

    override var bottomNavViewVisibility: Int = View.VISIBLE

    override fun onCreated() {
        binding.chatList.adapter = listAdapter
        observeData()
        observeEvents()
    }

    private fun observeEvents() {
        viewModel.onChatClick.observe(viewLifecycleOwner){
            openChannel(it)
        }
        binding.buttonNewChannel.setOnClickListener { openCreateChannelFragment() }
    }

    private fun openCreateChannelFragment() {
        navigate {
            ChannelsFragmentDirections.actionNavigationChatsToNavigationChannelCreate()
        }
    }

    private fun observeData() {
        viewModel.channels.observe(viewLifecycleOwner){ list ->
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