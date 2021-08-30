package pro.inmost.android.visario.data.api.dto.responses.websockets.channel.payloads

data class CreateChannelMembershipPayload(
    val ChannelArn: String,
    val CreatedTimestamp: String,
    val InvitedBy: InvitedBy,
    val LastUpdatedTimestamp: String,
    val Member: Member,
    val Type: String
)

data class InvitedBy(
    val Arn: String,
    val Name: String
)
data class Member(
    val Arn: String,
    val Name: String
)