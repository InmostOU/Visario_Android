package pro.inmost.android.visario.ui.screens.account

import android.content.Context
import android.net.Uri
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentAccountBinding
import pro.inmost.android.visario.ui.dialogs.ImagePickerDialog
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.main.MainActivity
import pro.inmost.android.visario.ui.screens.auth.AuthListener
import pro.inmost.android.visario.ui.utils.*


class AccountFragment : BaseFragment<FragmentAccountBinding>(R.layout.fragment_account) {
    private val viewModel: AccountViewModel by viewModel()
    private var authListener: AuthListener? = null
    override var bottomNavViewVisibility: Int = View.VISIBLE


    override fun onCreated() {
        binding.viewModel = viewModel
        observeEvents()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadProfile()
    }

    private fun observeEvents() {
        viewModel.logout.observe(viewLifecycleOwner){
            authListener?.onLogout()
        }
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.menu_profile_edit -> openEditInfoFragment()
                R.id.menu_log_out -> viewModel.logout()
            }
            true
        }
        binding.privacyLayout.setOnClickListener { openSecurityFragment() }

        binding.buttonChangeImage.setOnClickListener { openMediaPicker() }
    }

    private fun openMediaPicker() {
        ImagePickerDialog.show(childFragmentManager){ result ->
            viewModel.changePhoto(result)
        }
    }


    private fun openSecurityFragment() {
        navigate {
            AccountFragmentDirections.actionNavigationSettingsToNavigationSecurity()
        }
    }

    private fun openEditInfoFragment() {
        navigate {
            AccountFragmentDirections.actionNavigationSettingsToNavigationProfileInfoEdit()
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