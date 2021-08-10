package pro.inmost.android.visario.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import pro.inmost.android.visario.R

class DeleteDialog(
    private val context: Context,
    private val message: String
) {
    fun show(callback: () -> Unit) {
        AlertDialog.Builder(context)
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