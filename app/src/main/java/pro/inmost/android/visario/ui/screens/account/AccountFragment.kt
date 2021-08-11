package pro.inmost.android.visario.ui.screens.account

import android.content.Context
import android.graphics.PorterDuff
import android.view.View
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentAccountBinding
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.main.MainActivity
import pro.inmost.android.visario.ui.screens.auth.AuthListener
import pro.inmost.android.visario.ui.utils.navigate


class AccountFragment : BaseFragment<FragmentAccountBinding>(R.layout.fragment_account) {
    private val viewModel: AccountViewModel by viewModel()
    private var authListener: AuthListener? = null

    override var bottomNavViewVisibility: Int = View.VISIBLE

    override fun onCreated() {
        binding.viewModel = viewModel
        observeEvents()
    }

    private fun observeEvents() {
        setupToolbar()
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
    }

    private fun setupToolbar() {
        val editIcon = binding.toolbar.menu.getItem(0).icon
        val menuIcon = binding.toolbar.menu.getItem(1).icon
        var isShow = false
        var scrollRange = -1
        binding.appbar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, offset ->
            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange;
            }
            if (scrollRange + offset == 0) {
                //collapsed
                isShow = true
                editIcon.setColorFilter(
                    resources.getColor(R.color.surface),
                    PorterDuff.Mode.SRC_ATOP
                )
                menuIcon.setColorFilter(
                    resources.getColor(R.color.surface),
                    PorterDuff.Mode.SRC_ATOP
                )
            } else if (isShow) {
                //expanded
                isShow = false
                editIcon.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
                menuIcon.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
            }
        })
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