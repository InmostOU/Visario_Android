package pro.inmost.android.visario.ui.screens.account.settings.privacy.email

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentPrivacyEmailBinding
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.utils.extensions.navigateBack

class EmailSetupFragment : BaseFragment<FragmentPrivacyEmailBinding>(R.layout.fragment_privacy_email) {
    private val viewModel: EmailSetupViewModel by viewModel()

    override fun onCreated() {
        binding.viewModel = viewModel
        observeEvents()
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
    }
}