package pro.inmost.android.visario.domain.entities.channel

import pro.inmost.android.visario.domain.entities.message.Message

data class Channel (
    val url: String,
    val name: String,
    val mode: String,
    val privacy: String,
    val description: String,
    val isMember: Boolean,
    val isModerator: Boolean,
    var messages: List<Message>
)