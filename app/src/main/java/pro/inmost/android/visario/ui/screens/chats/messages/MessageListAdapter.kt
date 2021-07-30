package pro.inmost.android.visario.ui.screens.chats.messages

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pro.inmost.android.visario.data.network.chimeapi.messages.Message
import pro.inmost.android.visario.databinding.ListItemMessageBinding
import pro.inmost.android.visario.ui.utils.layoutInflater

class MessageListAdapter (private val viewModel: MessagesViewModel) :
    ListAdapter<Message, MessageListAdapter.MessageHolder>(ChannelsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        return MessageHolder(
            ListItemMessageBinding.inflate(parent.layoutInflater, parent, false),
            viewModel
        )
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MessageHolder(private val binding: ListItemMessageBinding, viewModel: MessagesViewModel) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.viewModel = viewModel
        }

        fun bind(item: Message) {
            binding.message = item
            binding.executePendingBindings()
        }
    }

    class ChannelsDiffUtil : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.messageId == newItem.messageId
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}