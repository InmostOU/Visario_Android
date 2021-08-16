package pro.inmost.android.visario.ui.screens.chats

import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentChatsBinding
import pro.inmost.android.visario.databinding.ListItemChatBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapterWithDiffUtil
import pro.inmost.android.visario.ui.entities.ChannelUI
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.utils.navigate

class ChatsFragment : BaseFragment<FragmentChatsBinding>(R.layout.fragment_chats) {
    private val viewModel: ChatsViewModel by viewModel()
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
            openChat(it)
        }
    }

    private fun observeData() {
        viewModel.channels.observe(viewLifecycleOwner){ list ->
            listAdapter.submitList(list)
        }
    }

    private fun openChat(channel: ChannelUI) {
        navigate {
            ChatsFragmentDirections.actionNavigationChatsToNavigationMessages(
                channelName = channel.name,
                channelUrl = channel.url
            )
        }
    }

}