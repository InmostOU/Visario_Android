package pro.inmost.android.visario.ui.screens.meet.meeting

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
import pro.inmost.android.visario.ui.entities.contact.ContactUI
import pro.inmost.android.visario.ui.utils.extensions.navigateBack
import pro.inmost.android.visario.ui.utils.extensions.toast


class MeetingFragment : BaseFragment<FragmentMeetingBinding>(R.layout.fragment_meeting) {
    private val viewModel: MeetingViewModel by viewModel()
    private val gridAdapter: MeetingMembersGridAdapter by lazy {
        MeetingMembersGridAdapter(binding.membersLayout, viewModel)
    }
    private val listAdapter =
        GenericListAdapter<ContactUI, ListItemMeetingMemberBinding>(R.layout.list_item_meeting_member) { item, binding ->
            binding.model = item
            binding.viewModel = viewModel
        }

    private val args: MeetingFragmentArgs by navArgs()

    override fun onCreated() {
        updateTitle(args.name)
        binding.viewModel = viewModel
        binding.bottomSheetMemberList.adapter = listAdapter
        observeEvents()
    }

    private fun updateTitle(title: String) {
        binding.toolbar.title = title
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
        viewModel.memberJoinEvent.observe(viewLifecycleOwner) { member ->
            lifecycleScope.launch {
                addMember(member)
                updateSubtitle()
                updateBottomSheetTitle()
                if (member.firstName != "Me"){
                    toast(getString(R.string.member_joined, member.fullName))
                }
            }
        }

        viewModel.memberLeaveEvent.observe(viewLifecycleOwner) { member ->
            lifecycleScope.launch {
                removeMember(member)
                updateSubtitle()
                updateBottomSheetTitle()
                toast(getString(R.string.member_left, member.fullName))
            }
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_meeting_add_member -> {
                    viewModel.addMember()
                }
            }
            true
        }

        binding.toolbar.setNavigationOnClickListener { navigateBack() }

        binding.hangUpButton.setOnClickListener { leaveMeeting() }
    }

    private fun addMember(member: ContactUI) {
        if (gridAdapter.membersCount < gridAdapter.maxItemsCount){
            gridAdapter.addItem(member)
        } else {
            listAdapter.addItem(member)
        }
        updateBottomSheetVisibility()
    }

    private fun removeMember(member: ContactUI) {
        val deleted = gridAdapter.deleteItem(member)
        if (deleted){
            moveMemberFromBottomSheetToMainGrid()
        } else {
            listAdapter.deleteItem(member)
        }
        updateBottomSheetVisibility()
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