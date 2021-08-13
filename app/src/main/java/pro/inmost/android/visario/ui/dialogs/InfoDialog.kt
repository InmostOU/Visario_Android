package pro.inmost.android.visario.ui.dialogs

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pro.inmost.android.visario.R

class InfoDialog(
    private val context: Context,
    private val title: String,
    private val message: String
) {

    fun show(callback: () -> Unit) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setIconAttribute(R.attr.colorSecondaryVariant)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                callback()
                dialog.dismiss()
            }.show()
    }
}