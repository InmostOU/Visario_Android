package pro.inmost.android.visario.ui.dialogs.alerts

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pro.inmost.android.visario.R

class LeaveChannelDialog(
    private val context: Context
) {
    fun show(callback: () -> Unit) {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.leave)
            .setIcon(R.drawable.ic_baseline_logout_24)
            .setMessage(R.string.dialog_message_leave_channel)
            .setCancelable(false)
            .setPositiveButton(R.string.leave) { dialog, _ ->
                callback()
            }.setNegativeButton(R.string.cancel){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}