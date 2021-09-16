package pro.inmost.android.visario.data.api.dto.requests.profile

/**
 * Update profile request
 *
 * @property username - require, must be unique
 * @property firstName - require
 * @property lastName - can be empty
 * @property birthday - user date of birth
 * @property about - short brief
 * @property showEmailTo - must be CONTACTS, EVERYONE or NO_ONE
 * @property showPhoneNumberTo - must be CONTACTS, EVERYONE or NO_ONE
 * @constructor Create empty Update profile request
 */
data class UpdateProfileRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val birthday: Long,
    val about: String,
    val showEmailTo: String,
    val showPhoneNumberTo: String
)
