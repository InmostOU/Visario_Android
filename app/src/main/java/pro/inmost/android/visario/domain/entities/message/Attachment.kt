package pro.inmost.android.visario.domain.entities.message

data class Attachment(
    val path: String,
    val type: AttachmentType
){
    enum class AttachmentType{
        IMAGE,
        VIDEO,
        DOC,
        OTHER
    }
}
