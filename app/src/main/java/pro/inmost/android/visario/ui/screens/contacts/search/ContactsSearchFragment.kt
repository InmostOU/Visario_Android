package pro.inmost.android.visario.ui.screens.contacts.search

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentContactsSearchBinding
import pro.inmost.android.visario.databinding.ListItemContactFoundBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapterWithDiffUtil
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.entities.contact.ContactUI
import pro.inmost.android.visario.utils.extensions.navigate
import pro.inmost.android.visario.utils.extensions.navigateBack
import pro.inmost.android.visario.utils.extensions.onQueryChange
import pro.inmost.android.visario.utils.extensions.visibility
import pro.inmost.android.visario.utils.showKeyboard


/**
 * Fragment for search contacts by username
 *
 */
class ContactsSearchFragment :
    BaseFragment<FragmentContactsSearchBinding>(R.layout.fragment_contacts_search) {
    private val viewModel: ContactsSearchViewModel by viewModel()
    private val listAdapter =
        GenericListAdapterWithDiffUtil<ContactUI, ListItemContactFoundBinding>(R.layout.list_item_contact_found) { contact, binding ->
            binding.viewModel = viewModel
            binding.model = contact
        }

    override fun onResume() {
        super.onResume()
        viewModel.updateLastSearch()
    }

    override fun onCreated() {
        showKeyboard()
        binding.contactList.adapter = listAdapter
        observeEvents()
        observeData()
    }

    private fun observeData() {
        viewModel.contacts.observe(viewLifecycleOwner){ contacts ->
            binding.notFoundLayout.visibility(contacts.isEmpty())
            listAdapter.submitList(contacts)
        }
    }

    private fun observeEvents() {
        binding.searchView.onQueryChange { query ->
            viewModel.search(query)
        }
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
        viewModel.openContactEvent.observe(viewLifecycleOwner){ contact ->
            openContactDetails(contact)
        }
        viewModel.editContactEvent.observe(viewLifecycleOwner){ contact ->
            openEditFragment(contact)
        }
    }

    private fun openEditFragment(contact: ContactUI) {
        navigate {
            ContactsSearchFragmentDirections.actionNavigationContactsSearchToNavigationContactEdit(contact)
        }
    }

    private fun openContactDetails(contact: ContactUI) {
        navigate {
            ContactsSearchFragmentDirections.actionNavigationContactsSearchToNavigationContactDetail(contact)
        }
    }
}