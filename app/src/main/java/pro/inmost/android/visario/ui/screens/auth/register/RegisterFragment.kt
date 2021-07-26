package pro.inmost.android.visario.ui.screens.auth.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentRegisterBinding
import pro.inmost.android.visario.ui.dialogs.InfoDialog
import pro.inmost.android.visario.ui.utils.navigateUp

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModel.backToLogin.observe(viewLifecycleOwner) { navigateUp() }
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
            navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}