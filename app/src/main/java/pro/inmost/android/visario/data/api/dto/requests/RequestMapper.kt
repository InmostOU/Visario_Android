package pro.inmost.android.visario.data.api.dto.requests

import pro.inmost.android.visario.data.api.dto.requests.auth.RegistrationRequest
import pro.inmost.android.visario.domain.entities.user.Register


/**
 * Map register DTO to registration request
 *
 * @return Registration request DTO
 */
fun Register.toRegistrationRequest(): RegistrationRequest {
    return RegistrationRequest(
        username = this.username,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        password = this.password,
        matchingPassword = this.password
    )
}



