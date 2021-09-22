package pro.inmost.android.visario.data.entities.channel

data class ChannelMember(
    val userId: Long,
    val fullName: String,
    val username: String,
    val mod: Boolean,
    val admin: Boolean
)
