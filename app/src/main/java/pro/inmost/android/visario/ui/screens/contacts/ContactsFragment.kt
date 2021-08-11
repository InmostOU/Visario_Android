package pro.inmost.android.visario.ui.screens.contacts

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentContactsBinding
import pro.inmost.android.visario.databinding.ListItemContactBinding
import pro.inmost.android.visario.domain.entities.Contact
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.adapters.GenericListAdapter
import pro.inmost.android.visario.ui.entities.ContactUI
import pro.inmost.android.visario.ui.utils.*

class ContactsFragment : BaseFragment<FragmentContactsBinding>(R.layout.fragment_contacts) {
    private val viewModel: ContactsViewModel by viewModel()
    private val listAdapter =
        GenericListAdapter<ContactUI, ListItemContactBinding>(R.layout.list_item_contact) { contact, binding ->
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
        val searchItem = binding.toolbar.menu.findItem(R.id.menu_search_contact)
        val searchView = searchItem.actionView as SearchView
        searchView.onQueryChange { query ->
            lifecycleScope.launchWhenResumed {
                val result = viewModel.search(query)
                // binding.notFoundLayout.visibility(result.isEmpty())
                listAdapter.submitList(result)
            }
        }

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