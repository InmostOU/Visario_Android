package pro.inmost.android.visario.domain.entities.message

data class Attachment(
    val path: String,
    val type: FileType
){
    enum class FileType {
        IMAGE,
        VIDEO,
        AUDIO,
        OTHER;

        companion object{
            fun getFromMimeType(mime: String): FileType {
                return when {
                    mime.contains("image") -> IMAGE
                    mime.contains("video") -> VIDEO
                    mime.contains("audio") -> AUDIO
                    else -> OTHER
                }
            }
        }
    }
}
