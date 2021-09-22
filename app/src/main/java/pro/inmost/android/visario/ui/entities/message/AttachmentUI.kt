package pro.inmost.android.visario.ui.entities.message

import android.net.Uri

data class AttachmentUI(
    val uri: Uri,
    val type: AttachmentTypeUI
){
    enum class AttachmentTypeUI{
        IMAGE,
        VIDEO,
        DOC
    }
}
