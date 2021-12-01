package pro.inmost.android.visario.domain.entities.message

data class Attachment(
    val path: String,
    val name: String,
    val type: FileType,
    val extension: String
){

    enum class FileType {
        IMAGE,
        VIDEO,
        AUDIO,
        OTHER;

        companion object{
            fun getFromMimeType(mime: String): FileType {
                return when {
                    mime.contains("image", true) -> IMAGE
                    mime.contains("video", true) -> VIDEO
                    mime.contains("audio", true) -> AUDIO
                    else -> OTHER
                }
            }
        }
    }
}
