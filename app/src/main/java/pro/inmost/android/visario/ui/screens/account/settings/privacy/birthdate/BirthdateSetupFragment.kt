package pro.inmost.android.visario.ui.screens.account.settings.privacy.birthdate

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentPrivacyBirthdateBinding
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.utils.extensions.navigateBack

class BirthdateSetupFragment : BaseFragment<FragmentPrivacyBirthdateBinding>(R.layout.fragment_privacy_birthdate) {
    private val viewModel: BirthdateSetupViewModel by viewModel()

    override fun onCreated() {
        binding.viewModel = viewModel
        observeEvents()
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
    }
}