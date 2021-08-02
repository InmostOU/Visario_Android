package pro.inmost.android.visario.ui.screens.chats

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pro.inmost.android.visario.databinding.ListItemChatBinding
import pro.inmost.android.visario.domain.entities.Channel
import pro.inmost.android.visario.ui.utils.layoutInflater

class ChatListAdapter(private val viewModel: ChatsViewModel) :
    ListAdapter<Channel, ChatListAdapter.ChannelHolder>(ChannelsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelHolder {
        return ChannelHolder(
            ListItemChatBinding.inflate(parent.layoutInflater, parent, false),
            viewModel
        )
    }

    override fun onBindViewHolder(holder: ChannelHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChannelHolder(private val binding: ListItemChatBinding, viewModel: ChatsViewModel) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.viewModel = viewModel
        }

        fun bind(item: Channel){
            binding.model = item
            binding.executePendingBindings()
        }
    }

    class ChannelsDiffUtil : DiffUtil.ItemCallback<Channel>() {
        override fun areItemsTheSame(oldItem: Channel, newItem: Channel): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Channel, newItem: Channel): Boolean {
            return oldItem == newItem
        }

    }
}