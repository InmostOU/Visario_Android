package pro.inmost.android.visario.core.data.chimeapi.channels.model

import pro.inmost.android.visario.core.data.chimeapi.messages.Message

data class Channel(
    val channelArn: String,
    val name: String?,
    val mode: String?,
    val privacy: String?,
    val metadata: String?
){
    var lastMessage: Message? = null

    fun getLastMessageContent() = lastMessage?.content ?: ""

    fun getLastMessageTime() = lastMessage?.getFormatDate() ?: ""
}