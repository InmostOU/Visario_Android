package pro.inmost.android.visario.ui.screens.contacts.search

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.pawegio.kandroid.onQueryChange
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentContactsSearchBinding
import pro.inmost.android.visario.databinding.ListItemContactFoundBinding
import pro.inmost.android.visario.domain.entities.Contact
import pro.inmost.android.visario.ui.adapters.GenericListAdapter
import pro.inmost.android.visario.ui.entities.ContactUI
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.screens.contacts.ContactsFragmentDirections
import pro.inmost.android.visario.ui.utils.*
import java.util.*

class ContactsSearchFragment :
    BaseFragment<FragmentContactsSearchBinding>(R.layout.fragment_contacts_search) {
    private val viewModel: ContactsSearchViewModel by viewModel()
    private val listAdapter =
        GenericListAdapter<ContactUI, ListItemContactFoundBinding>(R.layout.list_item_contact_found) { contact, binding ->
            binding.viewModel = viewModel
            binding.model = contact
        }

    override var bottomNavViewVisibility: Int = View.GONE

    override fun onCreated() {
        binding.contactList.adapter = listAdapter
        showKeyboard()
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