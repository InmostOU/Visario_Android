package pro.inmost.android.visario.data.api.services.websockets.channels.model.payloads


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
 * Info about who invited the user to the channel
 *
 * @property Arn - userArn of user who invited the member
 * @property Name - username of user who invited the member
 */
data class InvitedBy(
    val Arn: String,
    val Name: String
)

/**
 * Member of which was invited
 *
 * @property Arn - userArn of user who was invited
 * @property Name - username of user who was invited
 * @constructor Create empty Member
 */
data class Member(
    val Arn: String,
    val Name: String
)