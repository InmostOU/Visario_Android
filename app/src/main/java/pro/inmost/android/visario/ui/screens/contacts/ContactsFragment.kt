package pro.inmost.android.visario.ui.screens.contacts

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentContactsBinding
import pro.inmost.android.visario.databinding.ListItemContactBinding
import pro.inmost.android.visario.domain.entities.Contact
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.adapters.GenericListAdapter
import pro.inmost.android.visario.ui.utils.navigate

class ContactsFragment : BaseFragment<FragmentContactsBinding>(R.layout.fragment_contacts) {
    private val viewModel: ContactsViewModel by viewModel()
    private val listAdapter =
        GenericListAdapter<Contact, ListItemContactBinding>(R.layout.list_item_contact) { contact, binding ->
            binding.viewModel = viewModel
            binding.model = contact
        }

    override fun onCreated() {
        binding.contactList.adapter = listAdapter
        observeEvents()
        observeData()
    }

    private fun observeData() {
        viewModel.observeContacts().observe(viewLifecycleOwner){ contacts ->
            listAdapter.submitList(contacts)
        }
    }

    private fun observeEvents() {
        binding.buttonAddContact.setOnClickListener { openContactsSearchFragment() }
        viewModel.openContactEvent.observe(viewLifecycleOwner){ id ->
            openContactDetails(id)
        }
    }

    private fun openContactDetails(id: Int) {
        navigate {
            ContactsFragmentDirections.actionNavigationContactsToNavigationContactDetail(id)
        }
    }

    private fun openContactsSearchFragment() {
        navigate {
            ContactsFragmentDirections.actionNavigationContactsToNavigationContactsSearch()
        }
    }
}