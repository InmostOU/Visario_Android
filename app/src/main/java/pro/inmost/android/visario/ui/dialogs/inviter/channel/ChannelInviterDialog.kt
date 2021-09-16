package pro.inmost.android.visario.ui.dialogs.inviter.channel

import androidx.fragment.app.FragmentManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.BottomSheetContactsInviterBinding
import pro.inmost.android.visario.databinding.SelectListItemContactBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapterWithDiffUtil
import pro.inmost.android.visario.ui.base.BaseBottomSheet
import pro.inmost.android.visario.ui.entities.contact.ContactUI


/**
 * Dialog shows contacts you can invite to the selected channel
 *
 * @property channelArn channel url from AWS Chime to which you want to invite contacts
 * @constructor Create empty Channel inviter dialog
 */
class ChannelInviterDialog(private val channelArn: String) :
    BaseBottomSheet<BottomSheetContactsInviterBinding>(R.layout.bottom_sheet_contacts_inviter) {
    private val viewModel: ChannelInviterViewModel by viewModel()
    private val listAdapter =
        GenericListAdapterWithDiffUtil<ContactUI, SelectListItemContactBinding>(R.layout.select_list_item_contact) { contact, binding ->
            binding.viewModel = viewModel
            binding.model = contact
        }

    override fun onCreated() {
        binding.contactList.adapter = listAdapter
        viewModel.setChannel(channelArn)
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
            ChannelInviterDialog(channelUrl).show(fm, null)
        }
    }
}