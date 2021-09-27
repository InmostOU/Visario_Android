package pro.inmost.android.visario.data.api.dto.requests.messages

data class AttachmentData(
    val messageId: String,
    val fileName: String = "",
    val fileType: String = "",
    var url: String = ""
)