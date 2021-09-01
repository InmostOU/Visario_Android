package pro.inmost.android.visario.ui.screens.account.edit

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentProfileEditBinding
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.utils.extensions.navigateBack

class EditProfileFragment : BaseFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit) {
    private val viewModel: EditProfileViewModel by viewModel()

    override fun onCreated() {
        binding.viewModel = viewModel
        observeEvents()
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
        viewModel.quitEvent.observe(viewLifecycleOwner){ navigateBack() }
    }
}