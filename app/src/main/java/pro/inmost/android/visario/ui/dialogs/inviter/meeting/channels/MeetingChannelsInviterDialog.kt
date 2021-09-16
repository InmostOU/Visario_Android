package pro.inmost.android.visario.ui.dialogs.inviter.meeting.channels

import androidx.fragment.app.FragmentManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.BottomSheetChannelsInviterBinding
import pro.inmost.android.visario.databinding.SelectListItemChannelBinding
import pro.inmost.android.visario.ui.adapters.GenericListAdapterWithDiffUtil
import pro.inmost.android.visario.ui.base.BaseBottomSheet
import pro.inmost.android.visario.ui.entities.channel.ChannelUI


/**
 * Bottom sheet dialog with user's list of channels to which you can send an invitation to a meeting
 *
 * @property meetingId id of existing meeting
 */
class MeetingChannelsInviterDialog(private val meetingId: String) :
    BaseBottomSheet<BottomSheetChannelsInviterBinding>(R.layout.bottom_sheet_channels_inviter) {
    private val viewModel: MeetingChannelsInviterViewModel by viewModel()
    private val listAdapter =
        GenericListAdapterWithDiffUtil<ChannelUI, SelectListItemChannelBinding>(R.layout.select_list_item_channel) { channel, binding ->
            binding.viewModel = viewModel
            binding.model = channel
        }

    override fun onCreated() {
        binding.channelList.adapter = listAdapter
        viewModel.setMeetingId(meetingId)
        observeEvents()
        observeData()

    }

    private fun observeData() {
        viewModel.loadChannels().observe(viewLifecycleOwner) {
            if (listAdapter.currentList.isEmpty()){
                listAdapter.submitList(it)
            }
        }
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener {  close() }
    }

    companion object{
        fun show(fm: FragmentManager, meetingId: String){
            MeetingChannelsInviterDialog(meetingId).show(fm, null)
        }
    }
}