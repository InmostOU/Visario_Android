package pro.inmost.android.visario.ui.screens.account.settings.security

import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_BIOMETRIC_ENROLL
import android.provider.Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.core.view.isVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentSecurityBinding
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.utils.extensions.navigate
import pro.inmost.android.visario.utils.extensions.navigateBack


/**
 * Fragment with privacy settings menu
 *
 */
class SecurityFragment : BaseFragment<FragmentSecurityBinding>(R.layout.fragment_security) {
    private val viewModel: SecurityViewModel by viewModel()
    private val biometricManager: BiometricManager by lazy {
        BiometricManager.from(requireContext())
    }

    override var bottomNavViewVisibility: Int = View.GONE

    private lateinit var settingsDeviceLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsDeviceLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        }
    }

    override fun onCreated() {
        observeEvents()
        setupBiometrics()
    }

    private fun setupBiometrics() {
        binding.fingerprintSwitch.isVisible = when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }

    private fun toggleBiometrics(requestBiometrics: Boolean) {
        if (biometricManager.canAuthenticate(BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED) {
            openDeviceSettings()
        } else {
            viewModel.toggleBiometrics(requestBiometrics)
        }
    }

    private fun openDeviceSettings() {
        // Prompts the user to create credentials that your app accepts.
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Intent(ACTION_BIOMETRIC_ENROLL).apply {
                putExtra(
                    EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                )
            }
        } else {
            Intent(Settings.ACTION_SECURITY_SETTINGS)
        }
        settingsDeviceLauncher.launch(intent)
    }

    private fun observeEvents() {
        binding.apply {
            toolbar.setNavigationOnClickListener { navigateBack() }
            privacyPhoneNumberLayout.setOnClickListener { openPhoneNumberSetupFragment() }
            privacyBirthdateLayout.setOnClickListener { openBirthdateSetupFragment() }
            privacyEmailLayout.setOnClickListener { openEmailSetupFragment() }
            privacyPasswordLayout.setOnClickListener { openPasswordSetupFragment() }
            fingerprintSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                toggleBiometrics(isChecked)
            }
        }
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