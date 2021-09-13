package pro.inmost.android.visario.ui.screens.meet.list

import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentMeetingListBinding
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.screens.meet.list.MeetingsBottomSheetMenu.MenuItem.*
import pro.inmost.android.visario.ui.utils.extensions.navigate

class ScheduledMeetingsFragment : BaseFragment<FragmentMeetingListBinding>(R.layout.fragment_meeting_list) {
    private val viewModel: ScheduledMeetingsViewModel by viewModel()

    override var bottomNavViewVisibility: Int = View.VISIBLE

    override fun onCreated() {
        binding.viewModel = viewModel
        observeEvents()
    }

    private fun observeEvents() {
        binding.buttonNewMeeting.setOnClickListener { openMeetingsMenu() }
    }

    private fun openMeetingsMenu() {
        MeetingsBottomSheetMenu.show(childFragmentManager){ result ->
            when(result){
                CREATE_NEW_MEETING -> { openCreateMeetingFragment() }
                JOIN_MEETING -> { openJoinMeetingFragment() }
                SCHEDULE_MEETING -> { openScheduleMeetingFragment() }
            }
        }
    }

    private fun openScheduleMeetingFragment() {

    }

    private fun openJoinMeetingFragment() {
        navigate {
            ScheduledMeetingsFragmentDirections.actionNavigationMeetingsToNavigationMeetingJoin()
        }
    }

    private fun openCreateMeetingFragment() {
        navigate {
            ScheduledMeetingsFragmentDirections.actionNavigationMeetingsToNavigationMeeting(null)
        }
    }
}