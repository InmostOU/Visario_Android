package pro.inmost.android.visario.ui.screens.auth.login

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentLoginBinding
import pro.inmost.android.visario.ui.activities.MainActivity
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.screens.auth.AuthListener
import pro.inmost.android.visario.utils.extensions.navigate
import pro.inmost.android.visario.utils.extensions.snackbar
import pro.inmost.android.visario.utils.extensions.toast


/**
 * Fragment for login existing user
 *
 * @constructor Create empty Login fragment
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val loginViewModel: LoginViewModel by viewModel()
    private var authListener: AuthListener? = null

    override fun onCreated() {
        binding.viewModel = loginViewModel
        subscribeEvents()
    }

    private fun subscribeEvents() {
        loginViewModel.loginSuccessful.observe(viewLifecycleOwner){
            authListener?.onLogin()
        }
        loginViewModel.showSnackbar.observe(viewLifecycleOwner) { snackbar(it) }
        binding.apply {
            buttonLogin.setOnClickListener {
                loginViewModel.login()
            }
            buttonRegister.setOnClickListener {
                openRegisterScreen()
            }
            buttonFingerprint.setOnClickListener {
                requestBiometrics()
            }
        }
    }

    private fun requestBiometrics() {
        val executor = ContextCompat.getMainExecutor(requireContext())
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    toast(R.string.auth_succeeded)
                    authListener?.onLogin()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    toast(R.string.auth_failed)
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_login))
            .setNegativeButtonText(getString(R.string.use_password))
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun openRegisterScreen() {
        navigate {
            LoginFragmentDirections.actionNavigationLoginToNavigationRegister()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity){
            authListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        authListener = null
    }

}