package pro.inmost.android.visario.domain.entities.channel

import pro.inmost.android.visario.domain.entities.message.Message

data class Channel (
    val url: String,
    val name: String,
    val mode: String,
    val privacy: String,
    var messages: List<Message>
)