package pro.inmost.android.visario.ui.screens.meet.list

import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentMeetingListBinding
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.screens.meet.list.MeetingsBottomSheetMenu.MenuItem.CREATE_NEW_MEETING
import pro.inmost.android.visario.ui.screens.meet.list.MeetingsBottomSheetMenu.MenuItem.JOIN_MEETING
import pro.inmost.android.visario.ui.utils.extensions.navigate

class MeetingsFragment : BaseFragment<FragmentMeetingListBinding>(R.layout.fragment_meeting_list) {
    private val viewModel: MeetingsViewModel by viewModel()

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
            }
        }
    }

    private fun openJoinMeetingFragment() {
        navigate {
            MeetingsFragmentDirections.actionNavigationMeetingsToNavigationMeetingJoin()
        }
    }

    private fun openCreateMeetingFragment() {
        navigate {
            MeetingsFragmentDirections.actionNavigationMeetingsToNavigationMeetingCreate()
        }
    }
}