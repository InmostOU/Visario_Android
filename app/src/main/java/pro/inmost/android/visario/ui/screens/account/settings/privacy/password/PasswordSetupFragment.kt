package pro.inmost.android.visario.ui.screens.account.settings.privacy.password

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentPrivacyPasswordBinding
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.utils.extensions.navigateBack
import pro.inmost.android.visario.utils.extensions.snackbar
import pro.inmost.android.visario.utils.extensions.toast

/**
 * Fragment for change user's password
 *
 */
class PasswordSetupFragment: BaseFragment<FragmentPrivacyPasswordBinding>(R.layout.fragment_privacy_password) {
    private val viewModel: PasswordSetupViewModel by viewModel()
    override fun onCreated() {
        binding.viewModel = viewModel
        observeEvents()
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
        viewModel.showMessage.observe(viewLifecycleOwner){
            snackbar(it)
        }
        viewModel.updatePasswordSuccess.observe(viewLifecycleOwner){
            toast(getString(R.string.password_updated))
            navigateBack()
        }
    }
}