package pro.inmost.android.visario.data.api.dto.responses.websockets.channel.payloads

data class ChannelMessagePayload(
    val MessageId: String,
    val ChannelArn: String,
    val CreatedTimestamp: String,
    val LastUpdatedTimestamp: String,
    val Persistence: String,
    val Redacted: Boolean,
    val Type: String,
    val Sender: Sender,
    val Content: String,
    val Metadata: String? = null
)

data class Sender(
    val Arn: String,
    val Name: String
)