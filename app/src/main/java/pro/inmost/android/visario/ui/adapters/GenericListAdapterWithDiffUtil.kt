package pro.inmost.android.visario.ui.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pro.inmost.android.visario.ui.entities.BaseEntity
import pro.inmost.android.visario.utils.extensions.layoutInflater


/**
 * Generic list adapter for [RecyclerView] with comparing items by [DiffUtil]
 *
 * @param T type of list item
 * @param B [ViewDataBinding] of list item layout
 * @property layRes id of list item layout
 * @property bind callback when item binding
 */
class GenericListAdapterWithDiffUtil<T : BaseEntity, B : ViewDataBinding>(
    @LayoutRes private val layRes: Int,
    inline val bind: (item: T, binding: B) -> Unit
) : ListAdapter<T, GenericViewHolder<B>>(BaseDiffUtil()) {

    val size get() = currentList.size

    fun find(item: T) = currentList.find { it.baseId == item.baseId }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<B> {
        val binding = DataBindingUtil.inflate<B>(parent.layoutInflater, layRes, parent, false)
        return GenericViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenericViewHolder<B>, position: Int) {
        bind(getItem(position), holder.binding)
    }

    class BaseDiffUtil<E : BaseEntity> : DiffUtil.ItemCallback<E>() {
        override fun areItemsTheSame(oldItem: E, newItem: E): Boolean {
            return oldItem.baseId == newItem.baseId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: E, newItem: E): Boolean {
            return oldItem == newItem
        }

    }

}