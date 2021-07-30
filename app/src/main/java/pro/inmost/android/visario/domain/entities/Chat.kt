package pro.inmost.android.visario.domain.entities

data class Chat (
    val arn: String,
    val name: String,
    val metadata: String?,
    val mode: ChatMode,
    var privacy: Privacy
)