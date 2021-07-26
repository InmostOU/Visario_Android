package pro.inmost.android.visario.ui.screens.auth

import pro.inmost.android.visario.core.domain.entities.auth.AuthResponse
import pro.inmost.android.visario.core.domain.entities.auth.LoginRequest
import pro.inmost.android.visario.core.domain.entities.auth.RegisterRequest

interface Authenticator {
    suspend fun login(request: LoginRequest): AuthResponse
    suspend fun logout()
    suspend fun register(request: RegisterRequest): AuthResponse
}