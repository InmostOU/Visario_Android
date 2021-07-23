package pro.inmost.android.visario.data.network

import pro.inmost.android.visario.domain.model.entities.LoginRequest
import pro.inmost.android.visario.domain.model.entities.User
import pro.inmost.android.visario.ui.screens.auth.Authenticator

class AuthenticatorImpl : Authenticator {
    override suspend fun login(request: LoginRequest): LoginResponse {
        TODO("Not yet implemented")
    }

    override suspend fun register(request: User): RegisterResponse {
        TODO("Not yet implemented")
    }
}