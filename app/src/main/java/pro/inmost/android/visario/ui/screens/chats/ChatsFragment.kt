package pro.inmost.android.visario.ui.screens.chats

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentChatsBinding
import pro.inmost.android.visario.domain.entities.Channel
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.utils.navigate

class ChatsFragment : BaseFragment<FragmentChatsBinding>(R.layout.fragment_chats) {
    private val viewModel: ChatsViewModel by viewModel()
    private lateinit var listAdapter: ChatListAdapter

    override fun onCreated() {
        listAdapter = ChatListAdapter(viewModel)
        binding.chatList.adapter = listAdapter
        fetchData()
        observeEvents()
    }

    private fun observeEvents() {
        viewModel.onChatClick.observe(viewLifecycleOwner){
            openChat(it)
        }
    }

    private fun openChat(channel: Channel) {
        navigate {
            ChatsFragmentDirections.actionNavigationChatsToNavigationMessages(
                channelName = channel.name,
                channelUrl = channel.url
            )
        }
    }

    private fun fetchData() {
        viewModel.observeChats().observe(viewLifecycleOwner){ list ->
            listAdapter.submitList(list)
        }
    }

    override fun onStop() {
        viewModel.saveChannels()
        super.onStop()
    }

}