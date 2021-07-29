package pro.inmost.android.visario.ui.screens.chats.messages

import android.view.View
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

    override var bottomNavViewVisibility: Int = View.GONE

    override fun onCreated(binding: FragmentMessagesBinding) {
        binding.viewModel = viewModel
        updateTitle()
        setupRecyclerView()
        fetchData()
        observeEvents()
    }

    private fun setupRecyclerView() {
        listAdapter = MessageListAdapter(viewModel)
        binding.messageList.adapter = listAdapter
    }

    private fun updateTitle() {
        binding.appBar.title = args.channelName
    }

    private fun observeEvents() {
        binding.appBar.toolbar.setNavigationOnClickListener { navigateBack() }
    }

    private fun fetchData() {
        viewModel.getMessages(args.channelArn).observe(viewLifecycleOwner){ messages ->
            listAdapter.submitList(messages)
        }
        //for test
        viewModel.myNewMessages.observe(viewLifecycleOwner){ message ->
            listAdapter.submitList(
                listAdapter.currentList.toMutableList().apply { add(0,message) }
            ){
                binding.messageList.smoothScrollToPosition(0)
            }
        }
    }
}