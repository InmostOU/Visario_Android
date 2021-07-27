package pro.inmost.android.visario.core.domain.entities

data class Chat (
    val arn: String,
    val name: String,
    val metadata: String?,
    val mode: ChatMode,
    var privacy: Privacy
)