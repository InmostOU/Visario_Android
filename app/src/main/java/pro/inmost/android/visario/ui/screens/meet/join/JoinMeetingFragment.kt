package pro.inmost.android.visario.ui.screens.meet.join

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentMeetingJoinBinding
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.utils.extensions.navigate
import pro.inmost.android.visario.utils.extensions.navigateBack
import pro.inmost.android.visario.utils.extensions.toast
import pro.inmost.android.visario.utils.showKeyboard


/**
 * Fragment for join to existing meeting (by url)
 *
 */
class JoinMeetingFragment : BaseFragment<FragmentMeetingJoinBinding>(R.layout.fragment_meeting_join) {
    private val viewModel: JoinMeetingViewModel by viewModel()

    override fun onCreated() {
        showKeyboard()
        binding.viewModel = viewModel
        observeEvents()
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
        viewModel.joinMeeting.observe(viewLifecycleOwner){ openMeetingFragment(it) }
        viewModel.showToast.observe(viewLifecycleOwner){ toast(it) }
    }

    private fun openMeetingFragment(meetingId: String) {
        navigate {
            JoinMeetingFragmentDirections.actionNavigationMeetingJoinToNavigationMeeting(meetingId)
        }
    }
}