package pro.inmost.android.visario.data.api.dto.responses.websockets.channel.payloads


/**
 * DTO for web-socket message (Channel message events)
 *
 */
data class ChannelMessagePayload(
    val MessageId: String,
    val ChannelArn: String,
    val CreatedTimestamp: String,
    val LastUpdatedTimestamp: String,
    val Persistence: String,
    val Redacted: Boolean,
    val Type: String,
    val Sender: Sender,
    val Content: String?,
    val Metadata: String? = null
)

/**
 * Info about message sender
 *
 * @property Arn - userArn
 * @property Name - username
 *
 */
data class Sender(
    val Arn: String,
    val Name: String
)