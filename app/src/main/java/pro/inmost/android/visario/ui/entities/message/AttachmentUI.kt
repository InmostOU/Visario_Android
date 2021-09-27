package pro.inmost.android.visario.ui.entities.message

data class AttachmentUI(
    val path: String,
    val type: AttachmentTypeUI
){
    enum class AttachmentTypeUI{
        IMAGE,
        VIDEO,
        OTHER
    }
}
