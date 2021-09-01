package pro.inmost.android.visario.ui.screens.meet.join

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentMeetingJoinBinding
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.utils.extensions.navigateBack
import pro.inmost.android.visario.ui.utils.extensions.toast
import pro.inmost.android.visario.ui.utils.showKeyboard

class JoinMeetingFragment : BaseFragment<FragmentMeetingJoinBinding>(R.layout.fragment_meeting_join) {
    private val viewModel: JoinMeetingViewModel by viewModel()

    override fun onCreated() {
        showKeyboard()
        binding.viewModel = viewModel
        observeEvents()
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
        viewModel.openMeetingPrepare.observe(viewLifecycleOwner){ openMeetingPrepareFragment(it) }
        viewModel.showToast.observe(viewLifecycleOwner){ toast(it) }
    }

    private fun openMeetingPrepareFragment(url: String) {

    }
}