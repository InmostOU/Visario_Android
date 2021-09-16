package pro.inmost.android.visario.data.api.dto.responses.websockets.channel.payloads


/**
 * DTO for web-socket message (Channel membership events)
 *
 */
data class ChannelMembershipPayload(
    val ChannelArn: String,
    val CreatedTimestamp: String,
    val InvitedBy: InvitedBy,
    val LastUpdatedTimestamp: String,
    val Member: Member,
    val Type: String
)

/**
 * Invited by
 *
 * @property Arn - userArn of user who invited the member
 * @property Name - username of user who invited the member
 */
data class InvitedBy(
    val Arn: String,
    val Name: String
)

/**
 * Member
 *
 * @property Arn - userArn of user who was invited
 * @property Name - username of user who was invited
 * @constructor Create empty Member
 */
data class Member(
    val Arn: String,
    val Name: String
)