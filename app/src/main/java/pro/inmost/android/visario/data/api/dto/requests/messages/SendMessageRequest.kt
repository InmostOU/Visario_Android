package pro.inmost.android.visario.data.api.dto.requests.messages

/***
 * Send message request
 *
 * @property content
 * @property channelArn
 * @property metadata - put your generated messageId here to get it back through the web socket
 * in metadata field, because AWS Chime will send you his own-generated messageId,
 * and you need to find your message in local database for update
 * your temporary messageId to messageId by amazon
 * @constructor Create empty Send message request
 */
data class SendMessageRequest (
    val content: String,
    val channelArn: String,
    val metadata: String = ""
)
