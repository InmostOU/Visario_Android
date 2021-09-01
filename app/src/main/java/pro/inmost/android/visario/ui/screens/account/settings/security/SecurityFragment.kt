package pro.inmost.android.visario.ui.screens.account.settings.security

import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentSecurityBinding
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.utils.extensions.navigate
import pro.inmost.android.visario.ui.utils.extensions.navigateBack

class SecurityFragment : BaseFragment<FragmentSecurityBinding>(R.layout.fragment_security) {
    private val viewModel: SecurityViewModel by viewModel()

    override var bottomNavViewVisibility: Int = View.GONE

    override fun onCreated() {
        observeEvents()
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
        binding.privacyPhoneNumberLayout.setOnClickListener { openPhoneNumberSetupFragment() }
        binding.privacyBirthdateLayout.setOnClickListener { openBirthdateSetupFragment() }
        binding.privacyEmailLayout.setOnClickListener { openEmailSetupFragment() }
        binding.privacyPasswordLayout.setOnClickListener { openPasswordSetupFragment() }
    }

    private fun openPasswordSetupFragment() {
        navigate {
            SecurityFragmentDirections.actionNavigationSecurityToNavigationPrivacyPassword()
        }
    }

    private fun openEmailSetupFragment() {
        navigate {
            SecurityFragmentDirections.actionNavigationSecurityToNavigationPrivacyEmail()
        }
    }

    private fun openBirthdateSetupFragment() {
        navigate {
            SecurityFragmentDirections.actionNavigationSecurityToNavigationPrivacyBirthdate()
        }
    }

    private fun openPhoneNumberSetupFragment() {
        navigate {
            SecurityFragmentDirections.actionNavigationSecurityToNavigationPrivacyPhoneNumber()
        }
    }
}