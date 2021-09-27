package pro.inmost.android.visario.ui.dialogs.select.files

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher


class FileSelectorDialog(private val launcher: ActivityResultLauncher<Intent>, private val callback: (Uri) -> Unit) {

    private fun openSelector() {

    }

    companion object {
        fun select(launcher: ActivityResultLauncher<Intent>, callback: (Uri) -> Unit) {
            FileSelectorDialog(launcher, callback).openSelector()
        }
    }
}