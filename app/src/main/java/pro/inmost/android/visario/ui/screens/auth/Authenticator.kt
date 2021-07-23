package pro.inmost.android.visario.ui.screens.auth

import pro.inmost.android.visario.data.network.LoginResponse
import pro.inmost.android.visario.data.network.RegisterResponse
import pro.inmost.android.visario.domain.model.entities.LoginRequest
import pro.inmost.android.visario.domain.model.entities.User

interface Authenticator {
    suspend fun login(request: LoginRequest): LoginResponse
    suspend fun register(request: User): RegisterResponse
}