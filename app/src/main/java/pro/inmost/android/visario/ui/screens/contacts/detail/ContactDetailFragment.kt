package pro.inmost.android.visario.ui.screens.contacts.detail

import android.view.View
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentDetailContactBinding
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.utils.makeStatusBarTransparent
import pro.inmost.android.visario.ui.utils.navigateBack

class ContactDetailFragment : BaseFragment<FragmentDetailContactBinding>(R.layout.fragment_detail_contact) {
    private val viewModel: ContactDetailViewModel by viewModel()
    private val args: ContactDetailFragmentArgs by navArgs()

    override var bottomNavViewVisibility: Int = View.GONE

    override fun onCreated() {
        binding.viewModel = viewModel
        viewModel.loadContact(args.contactId)
        observeEvents()
    }

    private fun observeEvents() {
        binding.toolbarContactDetail.setNavigationOnClickListener { navigateBack() }
    }

    private fun openPopupMenu() {

    }
}