package pro.inmost.android.visario.ui.screens.meet.meeting

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import pro.inmost.android.visario.databinding.GridItemMeetingMemberBinding
import pro.inmost.android.visario.ui.entities.meeting.AttendeeUI
import pro.inmost.android.visario.ui.utils.extensions.layoutInflater


/**
 * Adapter that making grid for video surfaces
 *
 * @property parent layout in which the video surfaces will be placed
 * @property viewModel [MeetingViewModel] to bind with every grid item
 */
class MeetingMembersGridAdapter(
    private val parent: LinearLayout,
    private val viewModel: MeetingViewModel
) {
    private val context: Context get() = parent.context
    private val rows: List<LinearLayout> get() = parent.children.map { it as LinearLayout }.toList()
    val membersCount: Int
        get() = rows.sumOf { it.childCount }

    private val maxRowsCount = 3
    val maxItemsCount = 6

    private val isLeftOneRowWithTwoItems get() = rows.size == 1 && rows[0].childCount == 2

    private val isNeedNewRow: Boolean
        get() {
            val count = membersCount
            return count < 2 || count >= 4 && count % 2 == 0
        }

    /**
     * Add item to the grid
     *
     * @param item [AttendeeUI]
     */
    fun addItem(item: AttendeeUI) {
        if (isNeedNewRow) {
            val layout = createNewRow()
            addRow(layout)
            getItemBinding(layout)
        } else {
            rows.find { it.childCount == 1 }?.let { getItemBinding(it) }
        }?.let { itemBinding ->
            item.videoView = itemBinding.videoSurface
            itemBinding.model = item
            itemBinding.viewModel = viewModel
        }

    }

    /**
     * Delete item from the grid
     *
     * @param attendeeId id of attendee from meeting
     * @return
     */
    fun deleteItem(attendeeId: String): AttendeeUI? {
        var attendee: AttendeeUI? = null
        rows.forEach forEachOne@{ row ->
            row.forEach {
                DataBindingUtil.findBinding<GridItemMeetingMemberBinding>(it)?.let { binding ->
                    if (binding.model?.attendeeId == attendeeId) {
                        row.removeView(it)
                        if (row.isEmpty()) {
                            removeRow(row)
                        }
                        attendee = binding.model
                        return@forEachOne
                    }
                }
            }
        }
        if (attendee != null) formatGrid()
        return attendee
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

    private fun getItemBinding(view: ViewGroup): GridItemMeetingMemberBinding {
        return GridItemMeetingMemberBinding.inflate(view.layoutInflater, view, true)
            .apply {
                root.layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
                )
            }
    }
}