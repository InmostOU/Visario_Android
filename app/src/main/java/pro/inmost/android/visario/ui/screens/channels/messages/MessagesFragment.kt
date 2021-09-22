package pro.inmost.android.visario.ui.screens.channels.messages

import android.Manifest
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.navArgs
import com.pawegio.kandroid.toast
import com.vanniktech.emoji.EmojiPopup
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentMessagesBinding
import pro.inmost.android.visario.databinding.ListItemMessageBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapterWithDiffUtil
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.dialogs.alerts.SimpleAlertDialog
import pro.inmost.android.visario.ui.dialogs.inviter.channel.ChannelInviterDialog
import pro.inmost.android.visario.ui.dialogs.select.media.ImageSelectorDialog
import pro.inmost.android.visario.ui.entities.message.MessageUI
import pro.inmost.android.visario.ui.entities.message.MessageUIStatus
import pro.inmost.android.visario.ui.screens.channels.messages.BottomSheetAttachmentMenu.MenuItem.FILE
import pro.inmost.android.visario.ui.screens.channels.messages.BottomSheetAttachmentMenu.MenuItem.IMAGE
import pro.inmost.android.visario.ui.utils.extensions.*


/**
 * Fragment for chatting in channel
 *
 */
class MessagesFragment : BaseFragment<FragmentMessagesBinding>(R.layout.fragment_messages) {
    private val viewModel: MessagesViewModel by viewModel()
    private val args: MessagesFragmentArgs by navArgs()
    private val listAdapter =
        GenericListAdapterWithDiffUtil<MessageUI, ListItemMessageBinding>(R.layout.list_item_message) { message, binding ->
            binding.viewModel = viewModel
            binding.message = message
        }
    private val emojiPopup: EmojiPopup by lazy {
        EmojiPopup.Builder.fromRootView(binding.root)
            .setBackgroundColor(getColorFromAttr(R.attr.colorSurface))
            .build(binding.editTextMessage)
    }

    override fun onCreated() {
        binding.viewModel = viewModel
        binding.messageList.adapter = listAdapter
        updateTitle(args.channelName)
        showJoinButton(args.isMember)
        viewModel.setModerator(args.isModerator)
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
       /* viewModel.observeChannel(args.channelUrl).observe(viewLifecycleOwner){ channel ->
            binding.model = channel
        }
*/
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
                R.id.menu_meeting_create -> {
                    createNewMeeting()
                }
                R.id.menu_channel_invite -> {
                    showInvitationDialog(viewModel.currentChannelArn)
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
        viewModel.toggleEmojisView.observe(viewLifecycleOwner){
            emojiPopup.toggle()
        }
        viewModel.openChannelInfoEvent.observe(viewLifecycleOwner){ channelArn ->
            openChannelInfoFragment(channelArn)
        }
        viewModel.notificationEvent.observe(viewLifecycleOwner){ snackbar(it) }

        viewModel.openAttachmentMenu.observe(viewLifecycleOwner){
            openAttachmentMenu()
        }
    }

    private fun openAttachmentMenu() {
        BottomSheetAttachmentMenu.show(childFragmentManager){ result ->
            when(result){
                IMAGE -> { selectMedia() }
                FILE -> { selectFile() }
            }
        }
    }

    private fun selectFile() {

    }

    private fun selectMedia() {
        checkPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA){ granted ->
            if (granted){
                ImageSelectorDialog.show(childFragmentManager, true){ uri ->
                    viewModel.attachImage(uri)
                }
            } else {
                snackbarWithAction(R.string.permissions_denied, R.string.allow){ selectMedia() }
            }
        }

    }

    private fun openChannelInfoFragment(channelArn: String) {
        navigate {
            MessagesFragmentDirections.actionNavigationMessagesToNavigationChannelInfo(channelArn)
        }
    }

    private fun createNewMeeting() {
        viewModel.createNewMeeting()
    }

    private fun openPopupMenu(view: View, message: MessageUI) {
        PopupMenu(requireContext(), view).apply {
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_copy_message -> copyMessage(message.text)
                    R.id.menu_edit_message -> editMessage(message)
                    R.id.menu_resend_message -> resendMessage(message)
                    R.id.menu_delete_message -> deleteMessage(message)
                    R.id.menu_delete_message_local -> deleteFailedMessage(message)
                }
                true
            }
            val menu = when {
                message.fromCurrentUser -> {
                    when {
                        message.status == MessageUIStatus.FAIL -> R.menu.popup_message_failed
                        message.isMeetingInvitation -> R.menu.popup_message_invitation_my
                        else -> R.menu.popup_message_my
                    }
                }
                message.isMeetingInvitation -> R.menu.popup_message_invitation_other
                else -> R.menu.popup_message_other
            }
            inflate(menu)
            insertMenuItemIcons(requireContext())
            show()
        }
    }

    private fun deleteMessage(message: MessageUI) {
        SimpleAlertDialog(
            requireContext(),
            R.string.delete,
            R.string.delete,
            R.string.delete_message_confirm,
            R.drawable.ic_round_delete_forever_24
        ).show {
            viewModel.deleteMessage(message)
        }
    }

    private fun deleteFailedMessage(message: MessageUI) {
        viewModel.deleteLocalMessage(message)
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