package pro.inmost.android.visario.ui.screens.meet.meeting

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentMeetingBinding
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.dialogs.alerts.SimpleAlertDialog
import pro.inmost.android.visario.ui.utils.extensions.navigateBack
import pro.inmost.android.visario.ui.utils.extensions.toast


class MeetingFragment : BaseFragment<FragmentMeetingBinding>(R.layout.fragment_meeting) {
    private val viewModel: MeetingViewModel by viewModel()
    private val gridAdapter: MeetingMembersGridAdapter by lazy {
        MeetingMembersGridAdapter(binding.membersLayout, viewModel)
    }
    private val args: MeetingFragmentArgs by navArgs()

    override fun onCreated() {
        updateTitle(args.name)
        binding.viewModel = viewModel
        observeEvents()
    }

    private fun updateTitle(title: String) {
        binding.toolbar.title = title
    }

    private fun updateSubtitle() {
        val memberCount = gridAdapter.membersCount
        binding.toolbar.subtitle =
            resources.getQuantityString(R.plurals.members, memberCount, memberCount)
    }

    private fun observeEvents() {
        viewModel.memberJoinEvent.observe(viewLifecycleOwner) { member ->
            lifecycleScope.launch {
                gridAdapter.addItem(member)
                updateSubtitle()
                toast(getString(R.string.member_joined, member.fullName))
            }
        }

        viewModel.memberLeaveEvent.observe(viewLifecycleOwner) { member ->
            lifecycleScope.launch {
                gridAdapter.deleteItem(member)
                updateSubtitle()
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