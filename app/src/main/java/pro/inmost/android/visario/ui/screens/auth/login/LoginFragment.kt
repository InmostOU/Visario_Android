package pro.inmost.android.visario.ui.screens.auth.login

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import org.koin.android.ext.android.inject
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
    lateinit var googleLoginResultLauncher: ActivityResultLauncher<Intent>
    private val googleSignInClient: GoogleSignInClient by inject()
    private val facebookSignInClient: LoginManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        googleLoginResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK){
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            viewModel.loginViaGoogle(account)
        } catch (e: ApiException) {
            logError( "signInResult:failed code=" + e.statusCode)
            toast(R.string.login_failed)
        }
    }

    override fun onCreated() {
        binding.viewModel = viewModel
        setupFacebookLogin()
        subscribeEvents()
    }

    private fun loginViaGoogle() {
        googleLoginResultLauncher.launch(googleSignInClient.signInIntent)
    }

    private fun setupFacebookLogin() {
        val callbackManager = CallbackManager.Factory.create()
        facebookSignInClient.registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
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

    private fun subscribeEvents() {
        viewModel.openRegisterScreen.observe(viewLifecycleOwner){
            openRegisterScreen()
        }
        viewModel.loginSuccessful.observe(viewLifecycleOwner){
            authListener?.onLogin(it)
        }
        viewModel.showSnackbar.observe(viewLifecycleOwner) { snackbar(it) }

        binding.buttonLoginViaGoogle.setOnClickListener {
            loginViaGoogle()
        }
        binding.buttonLoginViaFacebook.setOnClickListener {
            LoginManager.getInstance().logIn(this, listOf(EMAIL))
        }
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