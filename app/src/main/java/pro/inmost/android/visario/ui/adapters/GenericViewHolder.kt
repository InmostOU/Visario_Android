package pro.inmost.android.visario.ui.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


/**
 * Generic view holder for [RecyclerView] list adapter
 *
 * @param B [ViewDataBinding] of list item layout
 * @property binding [ViewDataBinding] of list item layout
 */
class GenericViewHolder<B: ViewDataBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root)
