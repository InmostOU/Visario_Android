package pro.inmost.android.visario.core.domain.entities

import java.util.*

data class Message(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val timestamp: Long,
    val userId: String
)
