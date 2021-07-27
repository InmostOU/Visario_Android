package pro.inmost.android.visario.ui.screens.chats

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentChatsBinding
import pro.inmost.android.visario.ui.main.BaseFragment

class ChatsFragment : BaseFragment<FragmentChatsBinding>(R.layout.fragment_chats) {
    private val viewModel: ChatsViewModel by viewModel()
    private val listAdapter: ChannelListAdapter by inject()

    override fun onCreated(binding: FragmentChatsBinding) {
        binding.chatList.adapter = listAdapter
        fetchData()
    }

    private fun fetchData() {
        viewModel.observeChats().observe(viewLifecycleOwner){ list ->
            listAdapter.submitList(list)
        }
    }

}