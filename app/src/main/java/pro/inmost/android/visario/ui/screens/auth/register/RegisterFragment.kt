package pro.inmost.android.visario.ui.screens.auth.register

import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentRegisterBinding
import pro.inmost.android.visario.ui.dialogs.InfoDialog
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.utils.navigateBack

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreated() {
        binding.viewModel = viewModel
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModel.backToLogin.observe(viewLifecycleOwner) { navigateBack() }
        viewModel.showInfoDialogAndQuit.observe(viewLifecycleOwner) {
            showInfoDialog()
        }
    }

    private fun showInfoDialog() {
        InfoDialog(
            requireContext(),
            getString(R.string.confirm_email),
            getString(R.string.register_success)
        ).show {
            navigateBack()
        }
    }
}