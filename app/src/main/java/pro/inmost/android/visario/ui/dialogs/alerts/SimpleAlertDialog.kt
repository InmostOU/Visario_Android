package pro.inmost.android.visario.ui.dialogs.alerts

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pro.inmost.android.visario.R

/**
 * Simple alert dialog with cancel and action button
 *
 * @property context current [Context]
 * @property title short text to show on the top of dialog
 * @property positiveButtonText name of action button
 * @property message resId of text with detailed information for the user (can be empty)
 * @property icon resId of drawable icon
 */
class SimpleAlertDialog(
    private val context: Context,
    private val title: Int,
    private val positiveButtonText: Int,
    private val message: Int = 0,
    private val icon: Int = 0
) {

    fun show(callback: () -> Unit) {
        val builder = MaterialAlertDialogBuilder(context, R.style.CustomMaterialAlertDialog)
            .setTitle(title)
            .setCancelable(false)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                callback()
            }.setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
        if (icon != 0){
            builder.setIcon(icon)
        }
        if (message != 0){
            builder.setMessage(message)
        }
        builder.show()
    }
}