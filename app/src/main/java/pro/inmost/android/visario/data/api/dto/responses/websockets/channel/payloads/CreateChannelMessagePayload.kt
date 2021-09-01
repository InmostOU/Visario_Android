package pro.inmost.android.visario.data.api.dto.responses.websockets.channel.payloads

data class CreateChannelMessagePayload(
    val MessageId: String,
    val Content: String,
    val Sender: Sender,
    val ChannelArn: String,
    val CreatedTimestamp: String,
    val LastUpdatedTimestamp: String,
    val Persistence: String,
    val Redacted: Boolean,
    val Type: String
)

data class Sender(
    val Arn: String,
    val Name: String
)