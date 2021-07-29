package pro.inmost.android.visario.ui.screens.chats.messages

import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentMessagesBinding
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.utils.navigateBack

class MessagesFragment : BaseFragment<FragmentMessagesBinding>(R.layout.fragment_messages) {
    private val viewModel: MessagesViewModel by viewModel()
    private val args: MessagesFragmentArgs by navArgs()
    private lateinit var listAdapter: MessageListAdapter

    override fun onCreated(binding: FragmentMessagesBinding) {
        updateTitle(binding)
        setupRecyclerView(binding)
        fetchData()
        observeEvents(binding)
    }

    private fun setupRecyclerView(binding: FragmentMessagesBinding) {
        listAdapter = MessageListAdapter(viewModel)
        binding.messageList.adapter = listAdapter
    }

    private fun updateTitle(binding: FragmentMessagesBinding) {
        binding.appBar.title = args.channelName
    }

    private fun observeEvents(binding: FragmentMessagesBinding) {
        binding.appBar.toolbar.setNavigationOnClickListener { navigateBack() }
    }

    private fun fetchData() {
        viewModel.getMessages(args.channelArn).observe(viewLifecycleOwner){ messages ->
            listAdapter.submitList(messages)
        }
    }
}