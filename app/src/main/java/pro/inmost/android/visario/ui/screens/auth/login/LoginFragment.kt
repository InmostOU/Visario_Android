package pro.inmost.android.visario.ui.screens.auth.login

import android.content.Context
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentLoginBinding
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.main.MainActivity
import pro.inmost.android.visario.ui.screens.auth.AuthListener
import pro.inmost.android.visario.ui.utils.navigate

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModel()
    private var authListener: AuthListener? = null

    override var bottomNavViewVisibility: Int = View.GONE

    override fun onCreated() {
        binding.viewModel = viewModel
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModel.openRegisterScreen.observe(viewLifecycleOwner){
            openRegisterScreen()
        }
        viewModel.loginSuccessful.observe(viewLifecycleOwner){
            authListener?.onLogin()
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