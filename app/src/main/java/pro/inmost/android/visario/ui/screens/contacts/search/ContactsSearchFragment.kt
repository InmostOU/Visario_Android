package pro.inmost.android.visario.ui.screens.contacts.search

import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentContactsSearchBinding
import pro.inmost.android.visario.databinding.ListItemContactFoundBinding
import pro.inmost.android.visario.domain.entities.Contact
import pro.inmost.android.visario.ui.adapters.GenericListAdapter
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.screens.contacts.ContactsFragmentDirections
import pro.inmost.android.visario.ui.utils.navigate
import pro.inmost.android.visario.ui.utils.showKeyboard
import java.util.*

class ContactsSearchFragment :
    BaseFragment<FragmentContactsSearchBinding>(R.layout.fragment_contacts_search) {
    private val viewModel: ContactsSearchViewModel by viewModel()
    private val listAdapter =
        GenericListAdapter<Contact, ListItemContactFoundBinding>(R.layout.list_item_contact_found) { contact, binding ->
            binding.viewModel = viewModel
            binding.model = contact
        }

    override var bottomNavViewVisibility: Int = View.GONE

    override fun onCreated() {
        binding.contactList.adapter = listAdapter
        showKeyboard()
        observeEvents()
    }

    private fun observeEvents() {
        viewModel.openContactEvent.observe(viewLifecycleOwner){ id ->
            openContactDetails(id)
        }
    }

    private fun openContactDetails(id: Int) {
        navigate {
            ContactsSearchFragmentDirections.actionNavigationContactsSearchToNavigationContactDetail(id)
        }
    }
}