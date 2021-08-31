package pro.inmost.android.visario.ui.screens.channels.messages

import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentMessagesBinding
import pro.inmost.android.visario.databinding.ListItemMessageBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapterWithDiffUtil
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.dialogs.alerts.SimpleAlertDialog
import pro.inmost.android.visario.ui.dialogs.select.contacts.ContactsInviterDialog
import pro.inmost.android.visario.ui.entities.message.MessageUI
import pro.inmost.android.visario.ui.utils.extensions.navigateBack

class MessagesFragment : BaseFragment<FragmentMessagesBinding>(R.layout.fragment_messages) {
    private val viewModel: MessagesViewModel by viewModel()
    private val args: MessagesFragmentArgs by navArgs()
    private val listAdapter =
        GenericListAdapterWithDiffUtil<MessageUI, ListItemMessageBinding>(R.layout.list_item_message) { message, binding ->
            binding.viewModel = viewModel
            binding.message = message
        }

    override fun onCreated() {
        binding.viewModel = viewModel
        binding.messageList.adapter = listAdapter
        updateTitle(args.channelName)
        observeData()
        observeEvents()
    }

    private fun updateTitle(title: String) {
        if (title != binding.toolbar.title){
            binding.toolbar.title = title
        }
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
        binding.toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.menu_channel_leave -> { leaveChannel() }
                R.id.menu_channel_invite ->{ showInvitationDialog(viewModel.currentChannelUrl) }
            }

            true
        }
        viewModel.closeChannel.observe(viewLifecycleOwner){ navigateBack() }
    }

    private fun leaveChannel(){
        SimpleAlertDialog(
            requireContext(),
            title = R.string.leave,
            message = R.string.dialog_message_leave_channel,
            icon = R.drawable.ic_baseline_logout_24,
            positiveButtonText = R.string.leave
        ).show {
            viewModel.leaveChannel()
        }
    }

    private fun showInvitationDialog(channelUrl: String) {
        ContactsInviterDialog.show(childFragmentManager, channelUrl)
    }

    private fun observeData() {
        viewModel.observeMessages(args.channelUrl).observe(viewLifecycleOwner) { messages ->
            val needScroll = messages.size > listAdapter.size
            listAdapter.submitList(messages) {
                if (needScroll) scrollToBottom()
            }
        }
    }

    private fun scrollToBottom() {
        binding.messageList.smoothScrollToPosition(0)
    }
}