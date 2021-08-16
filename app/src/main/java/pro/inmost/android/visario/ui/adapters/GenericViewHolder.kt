package pro.inmost.android.visario.ui.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class GenericViewHolder<B: ViewDataBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root)
