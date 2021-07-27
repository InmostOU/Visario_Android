package pro.inmost.android.visario.ui.screens.chats

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pro.inmost.android.visario.core.data.chimeapi.auth.model.AuthResponse
import pro.inmost.android.visario.core.domain.entities.Chat
import pro.inmost.android.visario.databinding.ListItemChatBinding
import pro.inmost.android.visario.ui.utils.layoutInflater

class ChannelListAdapter(private val viewModel: ChatsViewModel) :
    ListAdapter<Chat, ChannelListAdapter.ChannelHolder>(ChannelsDiffUtil()) {

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

        fun bind(item: Chat){
            binding.model = item
            binding.executePendingBindings()
        }
    }

    class ChannelsDiffUtil : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.arn == newItem.arn
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }

    }
}