package pro.inmost.android.visario.ui.entities.message

data class AttachmentUI(
    val path: String,
    val name: String,
    val extension: String,
    val type: FileType
){

    val nameWithExtension: String
        get() = "$name.$extension"

    enum class FileType{
        IMAGE,
        VIDEO,
        AUDIO,
        OTHER
    }
}
