package pro.inmost.android.visario.ui.screens.channels.info.members

import androidx.navigation.fragment.navArgs
import com.pawegio.kandroid.onQueryChange
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentChannelMembersBinding
import pro.inmost.android.visario.databinding.ListItemChannelMemberBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapterWithDiffUtil
import pro.inmost.android.visario.ui.base.BaseFragment
import pro.inmost.android.visario.ui.dialogs.inviter.channel.ChannelInviterDialog
import pro.inmost.android.visario.ui.entities.contact.ContactUI
import pro.inmost.android.visario.ui.utils.extensions.navigate
import pro.inmost.android.visario.ui.utils.extensions.navigateBack

class ChannelMembersFragment : BaseFragment<FragmentChannelMembersBinding>(R.layout.fragment_channel_members) {
    private val viewModel: ChannelMembersViewModel by viewModel()
    private val args: ChannelMembersFragmentArgs by navArgs()
    private val listAdapter =
        GenericListAdapterWithDiffUtil<ContactUI, ListItemChannelMemberBinding>(R.layout.list_item_channel_member) { contact, binding ->
            binding.viewModel = viewModel
            binding.model = contact
        }

    override fun onCreated() {
        binding.moderator = args.isModerator
        binding.memberList.adapter = listAdapter
        observeData(args.channelArn)
        observeEvents()
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
        viewModel.openContactInfoEvent.observe(viewLifecycleOwner){ contact ->
            openContactInfo(contact)
        }
        binding.buttonAddMember.setOnClickListener { inviteMembers() }
        binding.searchView.onQueryChange { viewModel.searchMembers(it) }
    }

    private fun inviteMembers() {
        ChannelInviterDialog.show(childFragmentManager, args.channelArn)
    }

    private fun observeData(channelArn: String) {
        viewModel.loadMembers(channelArn).observe(viewLifecycleOwner){
            listAdapter.submitList(it)
        }
    }

    private fun openContactInfo(contact: ContactUI) {
        navigate {
            ChannelMembersFragmentDirections.actionNavigationChannelMembersToNavigationContactDetail(contact)
        }
    }
}