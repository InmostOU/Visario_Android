package pro.inmost.android.visario.ui.screens.meet.meeting

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import pro.inmost.android.visario.databinding.ListItemMeetingMemberBinding
import pro.inmost.android.visario.ui.entities.contact.ContactUI
import pro.inmost.android.visario.ui.utils.extensions.layoutInflater

class MeetingMembersGridAdapter(
    private val parent: LinearLayout,
    private val viewModel: MeetingViewModel
) {
    private val context: Context get() = parent.context
    private val rows: List<LinearLayout> get() = parent.children.map { it as LinearLayout }.toList()
    val membersCount: Int
        get() = rows.sumOf { it.childCount }

    private val maxRowsCount = 3

    private val isLeftOneRowWithTwoItems get() = rows.size == 1 && rows[0].childCount == 2

    private val isNeedNewRow: Boolean
        get() {
            val count = membersCount
            return count < 2 || count >= 4 && count % 2 == 0
        }

    fun addItem(item: ContactUI) {
        if (isNeedNewRow) {
            val layout = createNewRow()
            addRow(layout)
            getItemBinding(layout)
        } else {
            rows.find { it.childCount == 1 }?.let { getItemBinding(it) }
        }?.let { itemBinding ->
            itemBinding.model = item
            itemBinding.viewModel = viewModel
        }

    }

    fun deleteItem(member: ContactUI) {
        rows.forEach forEachOne@{ row ->
            row.forEach {
                DataBindingUtil.findBinding<ListItemMeetingMemberBinding>(it)?.let { binding ->
                    if (binding.model?.id == member.id) {
                        row.removeView(it)
                        if (row.isEmpty()) {
                            removeRow(row)
                        }
                        return@forEachOne
                    }
                }
            }
        }
        formatGrid()
    }

    private fun createNewRow(): LinearLayout {
        return LinearLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1.0f
            )
            visibility = if (rows.size >= maxRowsCount) View.GONE else View.VISIBLE
        }
    }

    private fun takeRowsWithOneItem(): List<LinearLayout> {
        return rows.filter { it.childCount == 1 }
    }

    private fun formatGrid() {
        if (isLeftOneRowWithTwoItems) {
            splitRow(rows[0])
        } else if (rows.size > 2) {
            val rowsWithOneItem = takeRowsWithOneItem()
            if (rowsWithOneItem.size > 1) {
                val row1 = rowsWithOneItem[0]
                val row2 = rowsWithOneItem[1]
                val child = row2.getChildAt(0)
                row2.removeView(child)
                row1.addView(child)
                removeRow(row2)
            }
        }
        rows.forEachIndexed { index, view ->
            if (index < maxRowsCount && view.visibility == View.GONE){
                view.visibility = View.VISIBLE
            }
        }
    }

    private fun removeRow(view: View) {
        parent.removeView(view)
    }

    private fun addRow(view: View) {
        parent.addView(view)
    }

    private fun splitRow(group: ViewGroup) {
        if (group.childCount < 2) return
        val lastItem = group.getChildAt(1).also { group.removeView(it) }
        val layout = createNewRow().apply { addView(lastItem) }
        addRow(layout)
        group.removeView(lastItem)
    }

    private fun getItemBinding(view: ViewGroup): ListItemMeetingMemberBinding {
        return ListItemMeetingMemberBinding.inflate(view.layoutInflater, view, true)
            .apply {
                root.layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
                )
            }
    }
}