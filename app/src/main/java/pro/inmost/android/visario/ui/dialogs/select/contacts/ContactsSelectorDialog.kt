package pro.inmost.android.visario.ui.dialogs.select.contacts

import androidx.fragment.app.FragmentManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.BottomSheetSelectContactsBinding
import pro.inmost.android.visario.databinding.SelectListItemContactBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapterWithDiffUtil
import pro.inmost.android.visario.ui.base.BaseBottomSheet
import pro.inmost.android.visario.ui.entities.contact.ContactUI

class ContactsSelectorDialog(private val channelUrl: String) :
    BaseBottomSheet<BottomSheetSelectContactsBinding>(R.layout.bottom_sheet_select_contacts) {
    private val viewModel: ContactsSelectorViewModel by viewModel()
    private val listAdapter =
        GenericListAdapterWithDiffUtil<ContactUI, SelectListItemContactBinding>(R.layout.select_list_item_contact) { contact, binding ->
            binding.viewModel = viewModel
            binding.model = contact
        }

    override fun onCreated() {
        binding.viewModel = viewModel
        binding.contactList.adapter = listAdapter
        viewModel.setChannel(channelUrl)
        observeEvents()
        observeData()

    }

    private fun observeData() {
        viewModel.loadContacts().observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener {  close() }
    }


    companion object{
        fun show(fm: FragmentManager, channelUrl: String){
            ContactsSelectorDialog(channelUrl).show(fm, null)
        }
    }
}