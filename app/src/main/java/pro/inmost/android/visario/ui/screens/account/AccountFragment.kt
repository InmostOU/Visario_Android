package pro.inmost.android.visario.ui.screens.account

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentAccountBinding
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.main.MainActivity
import pro.inmost.android.visario.ui.screens.auth.AuthListener
import pro.inmost.android.visario.ui.utils.MediaPicker
import pro.inmost.android.visario.ui.utils.log
import pro.inmost.android.visario.ui.utils.navigate


class AccountFragment : BaseFragment<FragmentAccountBinding>(R.layout.fragment_account) {
    private val viewModel: AccountViewModel by viewModel()
    private var authListener: AuthListener? = null
    override var bottomNavViewVisibility: Int = View.VISIBLE
    private val profileImageResult =
        registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    viewModel.changePhoto(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    log("image picker: " + ImagePicker.getError(data))
                }
                else -> {
                    log("image picker: Task Cancelled")
                }
            }
        }

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

        binding.buttonChangeImage.setOnClickListener { openImagePicker() }
    }

    private fun openImagePicker() {
        MediaPicker(this).startImagePicker(profileImageResult)
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