package pro.inmost.android.visario.ui.screens.settings

import android.content.Context
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentSettingsBinding
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.main.MainActivity
import pro.inmost.android.visario.ui.screens.auth.AuthListener

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {
    private val viewModel: SettingsViewModel by viewModel()
    private var authListener: AuthListener? = null

    override fun onCreated() {
        binding.viewModel = viewModel
        observeEvents()
    }

    private fun observeEvents() {
        viewModel.logout.observe(viewLifecycleOwner){
            authListener?.onLogout()
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