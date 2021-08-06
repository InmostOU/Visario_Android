package pro.inmost.android.visario.ui.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pro.inmost.android.visario.ui.utils.layoutInflater

open class GenericListAdapter<T, B : ViewDataBinding>(
    @LayoutRes private val layRes: Int,
    inline val bind: (item: T, binding: B) -> Unit
) : ListAdapter<T, GenericListAdapter.BaseViewHolder<B>>(BaseDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<B> {
        val binding = DataBindingUtil.inflate<B>(parent.layoutInflater, layRes, parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) {
        bind(getItem(position), holder.binding)
    }

    class BaseViewHolder<B: ViewDataBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root)

    class BaseDiffUtil<E> : DiffUtil.ItemCallback<E>() {
        override fun areItemsTheSame(oldItem: E, newItem: E): Boolean {
            return oldItem.toString() == newItem.toString()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: E, newItem: E): Boolean {
            return oldItem == newItem
        }

    }

}