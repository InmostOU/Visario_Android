package pro.inmost.android.visario.ui.screens.contacts.detail

import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentContactDetailBinding
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.dialogs.alerts.SimpleAlertDialog
import pro.inmost.android.visario.ui.entities.contact.ContactUI
import pro.inmost.android.visario.ui.utils.extensions.navigate
import pro.inmost.android.visario.ui.utils.extensions.navigateBack


/**
 * Fragment shows contact's detailed info
 *
 * @constructor Create empty Contact detail fragment
 */
class ContactDetailFragment : BaseFragment<FragmentContactDetailBinding>(R.layout.fragment_contact_detail) {
    private val viewModel: ContactDetailViewModel by viewModel()
    private val args: ContactDetailFragmentArgs by navArgs()

    override fun onCreated() {
        binding.viewModel = viewModel
        observeEvents()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadContact(args.contact)
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
        binding.toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId){
            //    R.id.menu_contact_invite -> inviteContact(args.contact)
                R.id.menu_contact_edit -> openEditContactFragment(args.contact)
                R.id.menu_contact_delete -> deleteContact(args.contact)
                R.id.menu_contact_block ->  blockContact(args.contact)
            }
            true
        }
        viewModel.openEditContactEvent.observe(viewLifecycleOwner){
            openEditContactFragment(it)
        }
        viewModel.closeFragmentEvent.observe(viewLifecycleOwner){ navigateBack() }
    }

    private fun blockContact(contact: ContactUI) {
        viewModel.blockContact(contact.username)
    }

    private fun inviteContact(contact: ContactUI) {

    }

    private fun deleteContact(contact: ContactUI) {
        SimpleAlertDialog(
            requireContext(),
            title = R.string.delete,
            message = R.string.delete_contact_confirm,
            positiveButtonText = R.string.delete,
            icon = R.drawable.ic_round_delete_forever_24
        ).show {
            viewModel.deleteContact(contact.id)
        }
    }

    private fun openEditContactFragment(contact: ContactUI) {
        navigate {
            ContactDetailFragmentDirections.actionNavigationContactDetailToNavigationContactEdit(contact)
        }
    }
}