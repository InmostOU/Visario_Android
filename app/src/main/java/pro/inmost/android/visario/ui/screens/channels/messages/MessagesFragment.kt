package pro.inmost.android.visario.ui.screens.channels.messages

import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.navArgs
import com.pawegio.kandroid.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentMessagesBinding
import pro.inmost.android.visario.databinding.ListItemMessageBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapterWithDiffUtil
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.dialogs.alerts.SimpleAlertDialog
import pro.inmost.android.visario.ui.dialogs.inviter.channel.ChannelInviterDialog
import pro.inmost.android.visario.ui.entities.message.MessageUI
import pro.inmost.android.visario.ui.entities.message.MessageUIStatus
import pro.inmost.android.visario.ui.utils.extensions.*


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
        showJoinButton(args.isMember)
        observeData()
        observeEvents()
    }

    private fun showJoinButton(show: Boolean) {
        viewModel.setJoined(show)
    }

    private fun updateTitle(title: String) {
        if (title != binding.toolbar.title) {
            binding.toolbar.title = title
        }
    }

    private fun observeData() {
        viewModel.observeMessages(args.channelUrl).observe(viewLifecycleOwner) { messages ->
            val needScroll = messages.size > listAdapter.size
            listAdapter.submitList(messages) {
                if (needScroll) scrollToBottom()
            }
        }
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_channel_leave -> {
                    leaveChannel()
                }
                R.id.menu_channel_invite -> {
                    showInvitationDialog(viewModel.currentChannelUrl)
                }
            }

            true
        }
        viewModel.closeChannel.observe(viewLifecycleOwner) { navigateBack() }
        viewModel.joinMeetingEvent.observe(viewLifecycleOwner) { meetingId ->
            openMeetingFragment(meetingId)
        }
        viewModel.openPopupMenu.observe(viewLifecycleOwner) {
            openPopupMenu(it.first, it.second)
        }
        viewModel.showKeyboard.observe(viewLifecycleOwner) {
            binding.editTextMessage.showKeyboardAndMoveCursorToEnd()
        }
    }

    private fun openPopupMenu(view: View, message: MessageUI) {
        PopupMenu(requireContext(), view).apply {
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_copy_message -> copyMessage(message.text)
                    R.id.menu_edit_message -> editMessage(message)
                    R.id.menu_resend_message -> resendMessage(message)
                    R.id.menu_delete_message_local -> deleteLocalMessage(message)
                }
                true
            }
            val menu = if (message.fromCurrentUser) {
                if (message.status == MessageUIStatus.FAIL) R.menu.popup_message_failed
                else R.menu.popup_message_my
            } else R.menu.popup_message_other
            inflate(menu)
            insertMenuItemIcons(requireContext())
            show()
        }
    }

    private fun deleteLocalMessage(message: MessageUI) {
        viewModel.deleteMessageLocal(message)
    }

    private fun resendMessage(message: MessageUI) {
        viewModel.resendMessage(message)
    }

    private fun editMessage(message: MessageUI) {
        viewModel.editMessage(message)
    }

    private fun copyMessage(message: String) {
        copyToClipboard(message)
        toast(R.string.copied)
    }

    private fun openMeetingFragment(meetingId: String) {
        navigate {
            MessagesFragmentDirections.actionNavigationMessagesToNavigationMeeting(meetingId)
        }
    }

    private fun leaveChannel() {
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
        ChannelInviterDialog.show(childFragmentManager, channelUrl)
    }

    private fun scrollToBottom() {
        binding.messageList.smoothScrollToPosition(0)
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateReadStatus()
    }
}