package pro.inmost.android.visario.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import pro.inmost.android.visario.R

class InfoDialog(
    private val context: Context,
    private val title: String,
    private val message: String
) {

    fun show(callback: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                callback()
                dialog.dismiss()
            }.show()
    }
}