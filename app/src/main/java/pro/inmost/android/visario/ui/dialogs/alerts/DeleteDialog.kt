package pro.inmost.android.visario.ui.dialogs.alerts

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pro.inmost.android.visario.R

class DeleteDialog(
    private val context: Context,
    private val message: String
) {
    fun show(callback: () -> Unit) {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.delete)
            .setIcon(R.drawable.ic_round_delete_forever_24)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.delete) { dialog, _ ->
                callback()
            }.setNegativeButton(R.string.cancel){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}