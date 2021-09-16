package pro.inmost.android.visario.ui.dialogs.alerts

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pro.inmost.android.visario.R


/**
 * Simple info dialog with message and one OK button
 *
 * @property context current [Context]
 * @property title short text to show on the top of dialog
 * @property message text with detailed information for the user
 */
class InfoDialog(
    private val context: Context,
    private val title: String,
    private val message: String
) {


    /**
     * Show dialog
     *
     * @param callback called when OK button clicked
     * @receiver
     */
    fun show(callback: () -> Unit) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                callback()
                dialog.dismiss()
            }.show()
    }
}