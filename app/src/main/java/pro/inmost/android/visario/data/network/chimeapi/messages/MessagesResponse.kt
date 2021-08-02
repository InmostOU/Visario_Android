package pro.inmost.android.visario.data.network.chimeapi.messages

import pro.inmost.android.visario.data.entities.MessageData

data class MessagesResponse(
    val status: Int,
    val message: String,
    val messages: List<MessageData>
)