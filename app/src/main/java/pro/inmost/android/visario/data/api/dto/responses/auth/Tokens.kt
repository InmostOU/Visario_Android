package pro.inmost.android.visario.data.api.dto.responses.auth


/**
 * Tokens from AWS Chime, which must be placed in request headers
 *
 * @property accessToken
 * @property refreshToken
 */
data class Tokens(
    val accessToken: String,
    val refreshToken: String
)