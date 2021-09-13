package pro.inmost.android.visario.ui.screens.meet.create

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentMeetingCreateBinding
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.utils.extensions.navigateBack
import pro.inmost.android.visario.ui.utils.showKeyboard

class CreateMeetingFragment : BaseFragment<FragmentMeetingCreateBinding>(R.layout.fragment_meeting_create) {
    private val viewModel: CreateMeetingViewModel by viewModel()

    override fun onCreated() {
        showKeyboard()
        binding.viewModel = viewModel
        observeEvents()
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
        viewModel.startMeeting.observe(viewLifecycleOwner){ name ->
            startMeeting(name)
        }
    }

    private fun startMeeting(name: String) {

    }
}