package pro.inmost.android.visario.ui.screens.meet.meeting

import android.Manifest.permission.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentMeetingBinding
import pro.inmost.android.visario.databinding.ListItemMeetingMemberBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapter
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.dialogs.alerts.SimpleAlertDialog
import pro.inmost.android.visario.ui.dialogs.inviter.meeting.channels.MeetingChannelsInviterDialog
import pro.inmost.android.visario.ui.dialogs.inviter.meeting.contacts.MeetingContactsInviterDialog
import pro.inmost.android.visario.ui.entities.meeting.AttendeeUI
import pro.inmost.android.visario.ui.utils.extensions.checkPermissions
import pro.inmost.android.visario.ui.utils.extensions.navigateBack
import pro.inmost.android.visario.ui.utils.extensions.snackbar
import pro.inmost.android.visario.ui.utils.extensions.toast


class MeetingFragment : BaseFragment<FragmentMeetingBinding>(R.layout.fragment_meeting) {
    private val viewModel: MeetingViewModel by viewModel()
    private val args: MeetingFragmentArgs by navArgs()
    private val gridAdapter: MeetingMembersGridAdapter by lazy {
        MeetingMembersGridAdapter(binding.membersLayout, viewModel)
    }
    private val listAdapter =
        GenericListAdapter<AttendeeUI, ListItemMeetingMemberBinding>(R.layout.list_item_meeting_member) { item, binding ->
            binding.model = item
            binding.viewModel = viewModel
        }

    override fun onCreated() {
        binding.viewModel = viewModel
        binding.bottomSheetMemberList.adapter = listAdapter
        observeEvents()
        startMeeting(args.meetingId)
    }

    private fun startMeeting(meetingId: String?) {
        checkPermissions(MODIFY_AUDIO_SETTINGS, RECORD_AUDIO, CAMERA) { granted ->
            if (granted) {
                if (meetingId.isNullOrBlank()){
                    viewModel.createMeeting(requireContext())
                } else {
                    viewModel.joinMeeting(requireContext(), meetingId)
                }
            } else {
                snackbar(R.string.permissions_denied)
            }
        }
    }

    private fun updateSubtitle() {
        val memberCount = gridAdapter.membersCount + listAdapter.itemCount
        binding.toolbar.subtitle =
            resources.getQuantityString(R.plurals.members, memberCount, memberCount)
    }

    private fun updateBottomSheetTitle() {
        val memberCount = listAdapter.itemCount
        binding.bottomSheetHeader.text =
            resources.getQuantityString(R.plurals.more_members, memberCount, memberCount)
    }

    private fun observeEvents() {
        viewModel.currentAttendee.observe(viewLifecycleOwner){
            binding.currentAttendee = it
            binding.executePendingBindings()
        }
        viewModel.memberJoinEvent.observe(viewLifecycleOwner) { member ->
            lifecycleScope.launch {
                addMember(member)
                updateSubtitle()
                updateBottomSheetTitle()
                toast(getString(R.string.member_joined, member.name))
            }
        }

        viewModel.memberLeftEvent.observe(viewLifecycleOwner) { attendeeId ->
            lifecycleScope.launch {
                removeMember(attendeeId)
                updateSubtitle()
                updateBottomSheetTitle()
            }
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_meeting_send_invitation -> {
                    inviteGroup(viewModel.meetingId)
                }
                R.id.menu_meeting_switch_camera -> {
                    viewModel.switchCamera()
                }
            }
            true
        }

        binding.toolbar.setNavigationOnClickListener { leaveMeeting() }

        binding.hangUpButton.setOnClickListener { leaveMeeting() }
    }

    private fun inviteGroup(meetingId: String) {
        MeetingChannelsInviterDialog.show(childFragmentManager, meetingId)
    }

    private fun inviteContact(meetingId: String) {
        MeetingContactsInviterDialog.show(childFragmentManager, meetingId)
    }

    private fun addMember(member: AttendeeUI) {
        if (gridAdapter.membersCount < gridAdapter.maxItemsCount){
            gridAdapter.addItem(member)
        } else {
            listAdapter.addItem(member)
        }
        updateBottomSheetVisibility()
    }

    private fun removeMember(attendeeId: String) {
        var attendee = gridAdapter.deleteItem(attendeeId)
        if (attendee != null){
            moveMemberFromBottomSheetToMainGrid()
        } else {
            attendee = listAdapter.getData().find { it.attendeeId == attendeeId }?.also {
                listAdapter.deleteItem(it)
            }
        }
        updateBottomSheetVisibility()
        toast(getString(R.string.member_left, attendee?.name))
    }

    private fun moveMemberFromBottomSheetToMainGrid() {
        listAdapter.firstItem?.let { member ->
            gridAdapter.addItem(member)
            listAdapter.deleteItem(member)
        }
    }

    private fun updateBottomSheetVisibility(){
        binding.bottomSheetVisible = listAdapter.itemCount > 0
    }

    private fun leaveMeeting() {
        SimpleAlertDialog(
            requireContext(),
            title = R.string.leave,
            positiveButtonText = R.string.leave,
            message = R.string.leave_meeting_confirm,
            icon = R.drawable.ic_baseline_logout_24
        ).show {
            lifecycleScope.launch {
                viewModel.leaveMeeting()
                navigateBack()
            }
        }
    }
}