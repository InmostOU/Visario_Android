package pro.inmost.android.visario.ui.dialogs.alerts

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pro.inmost.android.visario.R

class SimpleAlertDialog(
    private val context: Context,
    private val title: Int,
    private val positiveButtonText: Int,
    private val message: Int = 0,
    private val icon: Int = 0
) {

    fun show(callback: () -> Unit) {
        val builder = MaterialAlertDialogBuilder(context)
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