package pro.inmost.android.visario.ui.screens.account.account

import android.Manifest
import android.content.Context
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentAccountBinding
import pro.inmost.android.visario.ui.activities.MainActivity
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.dialogs.alerts.SimpleAlertDialog
import pro.inmost.android.visario.ui.dialogs.select.media.ImageSelectorDialog
import pro.inmost.android.visario.ui.screens.auth.AuthListener
import pro.inmost.android.visario.utils.extensions.checkPermissions
import pro.inmost.android.visario.utils.extensions.navigate
import pro.inmost.android.visario.utils.extensions.snackbarWithAction


/**
 * Fragment with user's profile info and settings menu
 *
 */
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
        viewModel.logoutEvent.observe(viewLifecycleOwner){
            authListener?.onLogout()
        }
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.menu_profile_edit -> openEditInfoFragment()
                R.id.menu_log_out -> { logout() }
            }
            true
        }
        binding.privacyLayout.setOnClickListener { openSecurityFragment() }

        binding.changePhotoButton.setOnClickListener { openMediaPicker() }
    }

    private fun logout() {
        SimpleAlertDialog(
            requireContext(),
            title = R.string.are_you_sure,
            positiveButtonText = R.string.log_out,
            icon = R.drawable.ic_baseline_logout_24
        ).show {
            viewModel.logout()
        }
    }

    private fun openMediaPicker() {
        checkPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA){ granted ->
            if (granted){
                ImageSelectorDialog.show(childFragmentManager, true){ result ->
                    viewModel.changePhoto(requireContext(), result)
                }
            } else {
                snackbarWithAction(R.string.permissions_denied, R.string.allow){ openMediaPicker() }
            }
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