package pro.inmost.android.visario.ui.screens.auth.login

import android.content.Context
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentLoginBinding
import pro.inmost.android.visario.ui.activities.MainActivity
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.screens.auth.AuthListener
import pro.inmost.android.visario.utils.extensions.navigate
import pro.inmost.android.visario.utils.extensions.snackbar
import pro.inmost.android.visario.utils.extensions.toast
import pro.inmost.android.visario.utils.log
import pro.inmost.android.visario.utils.logError


/**
 * Fragment for login existing user
 *
 * @constructor Create empty Login fragment
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModel()
    private var authListener: AuthListener? = null
    private val EMAIL = "email"

    override fun onCreated() {
        binding.viewModel = viewModel
        setupFacebookLogin()
        subscribeEvents()
    }

    private fun setupFacebookLogin() {
        val callbackManager = CallbackManager.Factory.create()
        binding.buttonLoginViaFacebook.apply {
            setPermissions(listOf(EMAIL))
            fragment = this@LoginFragment

            registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onCancel() {
                    log("login via facebook cancel")
                }

                override fun onError(error: FacebookException) {
                    logError("login via facebook error: ${error.message}")
                    toast(R.string.login_failed)
                }

                override fun onSuccess(result: LoginResult?) {
                    log("login via facebook success")
                    viewModel.loginViaFacebook(result)
                }
            })
        }
    }

    private fun subscribeEvents() {
        viewModel.openRegisterScreen.observe(viewLifecycleOwner){
            openRegisterScreen()
        }
        viewModel.loginSuccessful.observe(viewLifecycleOwner){
            authListener?.onLogin(it)
        }
        viewModel.showSnackbar.observe(viewLifecycleOwner) { snackbar(it) }
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