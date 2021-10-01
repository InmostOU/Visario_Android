package pro.inmost.android.visario.ui.adapters

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import pro.inmost.android.visario.utils.extensions.layoutInflater


/**
 * Generic list adapter for [RecyclerView]
 *
 * @param T type of list item
 * @param B [ViewDataBinding] of list item layout
 * @property layRes id of list item layout
 * @property bind callback when item binding
 */
open class GenericListAdapter<T, B : ViewDataBinding>(
    @LayoutRes private val layRes: Int,
    inline val bind: (item: T, binding: B) -> Unit
) : RecyclerView.Adapter<GenericViewHolder<B>>() {
    private var data = mutableListOf<T>()

    /**
     * Get list items
     *
     * @return item list
     */
    fun getData(): List<T> = data

    val firstItem get() = data.firstOrNull()
    val lastItem get() = data.lastOrNull()

    /**
     * Add item to the list
     *
     * @param item [T] type item
     */
    fun addItem(item: T){
        data.add(item)
        notifyItemInserted(data.size - 1)
    }

    /**
     * Delete item from the list
     *
     * @param item
     */
    fun deleteItem(item: T){
        val index = data.indexOf(item)
        data.remove(item)
        notifyItemRemoved(index)
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<B> {
        val binding = DataBindingUtil.inflate<B>(parent.layoutInflater, layRes, parent, false)
        return GenericViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenericViewHolder<B>, position: Int) {
        bind(data[position], holder.binding)
    }
}