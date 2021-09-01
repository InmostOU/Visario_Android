package pro.inmost.android.visario.ui.entities.channel

import pro.inmost.android.visario.ui.entities.BaseEntity
import pro.inmost.android.visario.ui.entities.message.MessageUI

data class ChannelUI (
    val url: String,
    val name: String,
    val mode: ChannelMode,
    val privacy: ChannelPrivacy,
    val description: String,
    val isMember: Boolean,
    val isModerator: Boolean,
    var messages: List<MessageUI> = listOf()
): BaseEntity{
    val lastMessage: String
        get() = messages.firstOrNull()?.text ?: ""
    val lastMessageTime: String
        get() = messages.firstOrNull()?.createdDateFormat ?: ""
    val newMessagesCount: String
        get() = messages.count { it.readByMe }.toString()

/* val lastMessage: LiveData<String>
        get() = messages.map { it.firstOrNull()?.text ?: "" }.asLiveData()
    val lastMessageTime: LiveData<String>
        get() = messages.map { it.firstOrNull()?.createdDateFormat ?: "" }.asLiveData()
    val newMessagesCount: LiveData<String>
        get() = messages.map { it.count { it.readByMe }.toString() }.asLiveData()*/


    override val baseId: String
        get() = url

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChannelUI

        if (url != other.url) return false
        if (name != other.name) return false
        if (mode != other.mode) return false
        if (privacy != other.privacy) return false
        if (description != other.description) return false
        if (isMember != other.isMember) return false
        if (isModerator != other.isModerator) return false
        if (messages != other.messages) return false
        if (lastMessage != other.lastMessage) return false
        if (lastMessageTime != other.lastMessageTime) return false
        if (newMessagesCount != other.newMessagesCount) return false
        if (baseId != other.baseId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = url.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + mode.hashCode()
        result = 31 * result + privacy.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + isMember.hashCode()
        result = 31 * result + isModerator.hashCode()
        result = 31 * result + messages.hashCode()
        result = 31 * result + lastMessage.hashCode()
        result = 31 * result + lastMessageTime.hashCode()
        result = 31 * result + newMessagesCount.hashCode()
        result = 31 * result + baseId.hashCode()
        return result
    }


}