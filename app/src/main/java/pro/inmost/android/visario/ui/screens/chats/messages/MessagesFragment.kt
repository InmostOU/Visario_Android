package pro.inmost.android.visario.ui.screens.chats.messages

import android.view.View
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentMessagesBinding
import pro.inmost.android.visario.databinding.ListItemContactBinding
import pro.inmost.android.visario.databinding.ListItemMessageBinding
import pro.inmost.android.visario.domain.entities.Contact
import pro.inmost.android.visario.domain.entities.Message
import pro.inmost.android.visario.ui.adapters.GenericListAdapter
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.utils.navigateBack

class MessagesFragment : BaseFragment<FragmentMessagesBinding>(R.layout.fragment_messages) {
    private val viewModel: MessagesViewModel by viewModel()
    private val args: MessagesFragmentArgs by navArgs()
    private val listAdapter =
        GenericListAdapter<Message, ListItemMessageBinding>(R.layout.list_item_message) { message, binding ->
            binding.viewModel = viewModel
            binding.message = message
        }

    override var bottomNavViewVisibility: Int = View.GONE

    override fun onCreated() {
        binding.viewModel = viewModel
        binding.messageList.adapter = listAdapter
        updateTitle(args.channelName)
        fetchData()
        observeEvents()
    }

    private fun updateTitle(title: String) {
        if (title != binding.appBar.title){
            binding.appBar.title = title
        }
    }

    private fun observeEvents() {
        binding.appBar.toolbar.setNavigationOnClickListener { navigateBack() }
    }

    private fun fetchData() {
        viewModel.observeChannel(args.channelUrl).observe(viewLifecycleOwner) { channel ->
            updateTitle(channel.name)
            val needScroll = channel.messages.size > listAdapter.currentList.size
            listAdapter.submitList(channel.messages) {
                if (needScroll) scrollToBottom()
            }
        }
    }

    private fun scrollToBottom() {
        binding.messageList.smoothScrollToPosition(0)
    }

    override fun onStop() {
        viewModel.saveChannel()
        super.onStop()
    }
}