package pro.inmost.android.visario.ui.screens.contacts.list

import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentContactsBinding
import pro.inmost.android.visario.databinding.ListItemContactBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapterWithDiffUtil
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.entities.contact.ContactUI
import pro.inmost.android.visario.utils.extensions.navigate


/**
 * Fragment show user's personal list of contacts
 *
 */
class ContactsFragment : BaseFragment<FragmentContactsBinding>(R.layout.fragment_contacts) {
    private val viewModel: ContactsViewModel by viewModel()
    private val listAdapter =
        GenericListAdapterWithDiffUtil<ContactUI, ListItemContactBinding>(R.layout.list_item_contact) { contact, binding ->
            binding.viewModel = viewModel
            binding.model = contact
        }

    override var bottomNavViewVisibility: Int = View.VISIBLE

    override fun onCreated() {
        binding.contactList.adapter = listAdapter
        observeEvents()
        observeData()
    }

    private fun observeData() {
        viewModel.contacts.observe(viewLifecycleOwner){ contacts ->
            listAdapter.submitList(contacts)
        }
    }

    private fun observeEvents() {
        binding.buttonAddContact.setOnClickListener { openContactsSearchFragment() }
        viewModel.openContactEvent.observe(viewLifecycleOwner){ contact ->
            openContactDetails(contact)
        }
    }

    private fun openContactDetails(contact: ContactUI) {
        navigate {
            ContactsFragmentDirections.actionNavigationContactsToNavigationContactDetail(contact)
        }
    }

    private fun openContactsSearchFragment() {
        navigate {
            ContactsFragmentDirections.actionNavigationContactsToNavigationContactsSearch()
        }
    }
}